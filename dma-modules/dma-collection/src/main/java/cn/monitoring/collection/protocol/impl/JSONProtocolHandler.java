package cn.monitoring.collection.protocol.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.monitoring.collection.config.DataConfig;
import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.domain.DataPoint;
import cn.monitoring.collection.protocol.IProtocolHandler;
import cn.monitoring.collection.service.IClickHouseService;
import cn.monitoring.collection.service.IDataPointService;
import cn.monitoring.collection.utils.DmaCollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JSON协议处理器
 * @author liru
 * @date 2024-12-15
 */
@Slf4j
@Component
public class JSONProtocolHandler implements IProtocolHandler {

    @Autowired
    private IDataPointService dataPointService;

    @Autowired
    private IClickHouseService clickHouseService;

    /**
     * 处理数据库ClickHouse结构
     *
     * @param collectTime 收集时间
     * @param info        收集器信息
     * @param data        数据
     */
    @Override
    public void handleDBStructure(long collectTime, CollectorInfo info, Object data) {
        //采用ClickHouse分区处理数据，不使用分表形式。 数据聚合以ModuleName分表。
        String tableName = DmaCollectionUtil.generateTableName(info.getModuleName());
        List<DataPoint> dataPoints = dataPointService.selectDataPointByTableName(tableName);
        // 将List转换为Map <pointCode, DataPoint>
        Map<String, DataPoint> dataPointMap = dataPoints.stream()
                .collect(java.util.stream.Collectors.toMap(DataPoint::getPointCode, dataPoint -> dataPoint));

        // 解析JSON数据
        JSON json = JSONUtil.parse(data);

        // 递归遍历JSON对象存入设置的dataPointMap中
        List<Map<String,DataPoint>> recursiveResults = new ArrayList<Map<String,DataPoint>>();
        recursiveTraversal(json,"baseData",tableName,dataPointMap,recursiveResults);

        // 检查并创建ClickHouse表结构
        boolean tableExists = clickHouseService.isTableExists(DataConfig.CLICKHOUSE_TABLE_PREFIX+tableName);
        if(!tableExists){
            String createTableSql = generateCreateTableSql(DataConfig.CLICKHOUSE_TABLE_PREFIX+tableName);
            clickHouseService.createNewTable(DataConfig.CLICKHOUSE_TABLE_PREFIX+tableName, createTableSql);
        }
        // 检查并添加ClickHouse表列
        for (DataPoint dataPoint : dataPointMap.values()) {
            String columnName = dataPoint.getPointName();
            String dataType = dataPoint.getDataType();
            boolean columnExists = clickHouseService.isColumnExists(DataConfig.CLICKHOUSE_TABLE_PREFIX+tableName,DataConfig.CLICKHOUSE_COLUMN_PREFIX+columnName);
            if(!columnExists){
                String addColumnSql = generateAddColumnSql(DataConfig.CLICKHOUSE_TABLE_PREFIX+tableName,DataConfig.CLICKHOUSE_COLUMN_PREFIX+columnName,dataType);
                clickHouseService.addColumnToTable(DataConfig.CLICKHOUSE_TABLE_PREFIX+tableName,addColumnSql);
            }
        }
    }

    /**
     * 处理数据 - 保存到ClickHouse
     * @param collectTime 收集时间
     * @param info 采集器信息
     * @param data 协议数据
     */
    @Override
    public void handle(long collectTime, CollectorInfo info, Object data) {
        //采用ClickHouse分区处理数据，不使用分表形式。 数据聚合以ModuleName分表。
        String tableName = DmaCollectionUtil.generateTableName(info.getModuleName());
        List<DataPoint> dataPoints = dataPointService.selectDataPointByTableName(tableName);
        // 将List转换为Map <pointCode, DataPoint>
        Map<String, DataPoint> dataPointMap = dataPoints.stream()
                .collect(java.util.stream.Collectors.toMap(DataPoint::getPointCode, dataPoint -> dataPoint));

        // 解析JSON数据
        JSON json = JSONUtil.parse(data);

        // 递归遍历JSON对象存入设置的dataPointMap中
        List<Map<String,DataPoint>> recursiveResults = new ArrayList<Map<String,DataPoint>>();
        recursiveTraversal(json,"baseData",tableName,dataPointMap,recursiveResults);

        // 根据解析结果保存数据到ClickHouse
        for (Map<String,DataPoint> result : recursiveResults) {
            String insertSql = generateInsertSql(DataConfig.CLICKHOUSE_TABLE_PREFIX+tableName,collectTime,info.getDmaTopic(),result);
            clickHouseService.insert(tableName,insertSql);
        }
    }

    /**
     * 生成插入表SQL语句
     *
     * @param tableName
     * @param collectTime
     * @param dmaTopic
     * @return
     */
    private String generateInsertSql(String tableName, long collectTime, String dmaTopic, Map<String, DataPoint> dataPointMap) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ").append(tableName).append(" (").append("topic,collect_time,");


        // 获取列名，使用DataPoint对象的属性名作为列名，拼接列名部分
        String columns = dataPointMap.values().stream()
                .map(dataPoint -> {
                    return DataConfig.CLICKHOUSE_COLUMN_PREFIX+dataPoint.getPointCode();
                })
                .collect(Collectors.joining(", "));
        sqlBuilder.append(columns).append(") VALUES ");

        // 拼接值部分，根据不同的数据类型进行相应的处理
        sqlBuilder.append("(").append("'").append(dmaTopic).append("'").append(",").append(collectTime).append(",");
        String values = dataPointMap.values().stream()
                .map(dataPoint -> {
                    Object value = dataPoint.getValue();
                    String dataType = dataPoint.getDataType();
                    if (value == null) {
                        return "NULL";
                    }
                    if (dataType.equalsIgnoreCase("int") || dataType.equalsIgnoreCase("integer")) {
                        return value.toString();
                    } else if (dataType.equalsIgnoreCase("long")) {
                        return value.toString();
                    } else if (dataType.equalsIgnoreCase("float")) {
                        return value.toString();
                    } else if (dataType.equalsIgnoreCase("double")) {
                        return value.toString();
                    } else if (dataType.equalsIgnoreCase("string")) {
                        return "'" + value.toString().replace("'", "\\'") + "'";
                    } else if (dataType.equalsIgnoreCase("boolean")) {
                        return ((Boolean) value)? "1" : "0";
                    } else if (dataType.equalsIgnoreCase("date")) {
                        return "'" + value.toString() + "'";
                    } else if (dataType.equalsIgnoreCase("datetime")) {
                        return "'" + value.toString() + "'";
                    } else if (dataType.equalsIgnoreCase("json")) {
                        return "'" + JSONUtil.toJsonStr(value) + "'";
                    }else {
                        // 对于不识别的数据类型，可以根据实际情况进行处理，这里暂时返回NULL并打印警告信息
                        log.error("不识别的数据类型: " + dataType + "，对应值将设置为NULL插入");
                        return "NULL";
                    }
                })
                .collect(Collectors.joining(", "));
        sqlBuilder.append(values).append(")");

        return sqlBuilder.toString();
    }

    public void recursiveTraversal(JSON json,String baseKey,String tableName,Map<String,DataPoint> dataPointMap,List<Map<String,DataPoint>> recursiveResults) {
        if (json instanceof JSONObject) {
            // 如果是JSON对象，遍历其键值对，并对值继续递归遍历
            JSONObject jsonObject = (JSONObject) json;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                log.info("键: " + key + ", 值: " + value);
                if (value instanceof JSON) {
                    recursiveTraversal((JSON) value,key, tableName,dataPointMap,recursiveResults);
                }else{
                    DataPoint dataPoint = dataPointMap.get(key);
                    dataPoint = convertToDataPoint(dataPoint,tableName,key,value);
                    // 过滤掉不支持的数据类型
                    if(dataPoint==null){
                        continue;
                    }
                    dataPointMap.put(key,dataPoint);
                    recursiveResults.get(recursiveResults.size()-1).put(key,dataPoint);
                }
            }
        } else if (json instanceof JSONArray) {
            // 如果是JSON数组，遍历数组元素，并对每个元素继续递归遍历
            JSONArray jsonArray = (JSONArray) json;
            for (int i = 0; i < jsonArray.size(); i++) {
                Object element = jsonArray.get(i);
                if (element instanceof JSON) {
                    Map<String,DataPoint> targetDataPointMap = new HashMap<>();
                    recursiveResults.add(targetDataPointMap);
                    recursiveTraversal((JSON) element, baseKey ,tableName,dataPointMap,recursiveResults);
                }else{
                    //数组类数据处理成JSON类型
                    DataPoint dataPoint = dataPointMap.get(baseKey);
                    dataPoint = convertToDataPoint(dataPoint,tableName,baseKey,jsonArray);
                    // 过滤掉不支持的数据类型
                    if(dataPoint==null){
                        continue;
                    }
                    dataPointMap.put(baseKey,dataPoint);
                    recursiveResults.get(recursiveResults.size()-1).put(baseKey,dataPoint);
                    break;
                }
            }
        }
    }

    /**
     * 将数据转换为DataPoint,并更新到mysql data_point表中
     * @param dataPoint
     * @param key
     * @param value
     * @return
     */
    private DataPoint convertToDataPoint(DataPoint dataPoint,String tableName, String key, Object value) {
        if(dataPoint==null){
            dataPoint = new DataPoint();
            dataPoint.setPointCode(key);
            dataPoint.setPointName(key);
            dataPoint.setDataType(extractDataType(value));
            dataPoint.setTableName(tableName);
            dataPoint.setIsActive(1);
            dataPoint.setCreateBy("DMA Collection");

            if(dataPoint.getDataType()==null){
                return null;
            }
            dataPointService.insertDataPoint(dataPoint);
        }else{
            //如果存在新建一个DataPoint对象，避免影响原有的DataPoint对象
            DataPoint dataPointTemp = new DataPoint();
            BeanUtils.copyProperties(dataPoint,dataPointTemp);
            dataPoint = dataPointTemp;
        }
        dataPoint.setValue(value);
        return dataPoint;
    }

    /**
     * 检查数据类型
     * @param value
     * @return
     */
    public static String extractDataType(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return "int";
        } else if (value instanceof BigDecimal) {
            return "float";
        }else if (value instanceof Float) {
            return "float";
        } else if (value instanceof Double) {
            return "double";
        } else if (value instanceof String) {
            return "string";
        } else if (value instanceof Boolean) {
            return "boolean";
        } else if (value instanceof Date) {
            if (hasTimeInfo(value)) {
                return "datetime";
            } else {
                return "date";
            }
        } else if (value instanceof Long) {
            return "long";
        } else if (value instanceof JSON) {
            return "json";
        }
        return null;
    }

    /**
     * 检查判断时间数据类型
     */
    public static boolean hasTimeInfo(Object dateObj) {
        if (dateObj instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = dateFormat.format((Date) dateObj);
            String dateTimeStr = dateTimeFormat.format((Date) dateObj);
            return dateTimeStr.length() > dateStr.length();
        }
        return false;
    }

    /**
     * 生成添加列的SQL语句
     */
    private String generateCreateTableSql(String tableName) {
        // 构造创建表的SQL语句，这里示例创建一个简单的表结构，使用MergeTree引擎
        StringBuilder createTableSqlBuffer = new StringBuilder();
        createTableSqlBuffer.append("CREATE TABLE IF NOT EXISTS ").append(tableName);
        createTableSqlBuffer.append("(");
        createTableSqlBuffer.append("    topic String,");     //传感器编号，使用字符串类型方便各种格式的编号表示
        createTableSqlBuffer.append("    collect_time UInt64");  //采集时间，使用无符号64位整数类型存储毫秒数，方便后续按时间查询和分析
        //可以添加更多相关字段，比如采集地点、所属设备等，根据实际业务拓展
        createTableSqlBuffer.append(") ");
        createTableSqlBuffer.append("ENGINE = MergeTree() ");
        createTableSqlBuffer.append("PARTITION BY toYYYYMMDD(toDateTime(collect_time / 1000)) "); //通过将毫秒数转换为秒后再获取日期进行分区，按照采集时间的日期进行分区，便于按天查询和管理数据
        createTableSqlBuffer.append("ORDER BY (collect_time, topic) ");  //先按采集时间排序，再按传感器编号排序，优化查询性能
        createTableSqlBuffer.append("PRIMARY KEY (collect_time, topic)"); //设置主键，与排序字段保持一致，方便数据查找和关联操作
        return createTableSqlBuffer.toString();
    }

    /**
     * 根据列名和数据类型生成添加列到ClickHouse表的SQL语句的方法
     *
     * @param columnName 列名
     * @param dataType 数据类型，如 "int"、"float"、"string" 等
     * @return 生成的添加列的SQL语句
     */
    private String generateAddColumnSql(String tableName,String columnName, String dataType) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("ALTER TABLE ").append(tableName).append(" ADD COLUMN ");
        sqlBuilder.append(columnName).append(" ");
        switch (dataType) {
            case "int":
                sqlBuilder.append("Int32");
                break;
            case "float":
                sqlBuilder.append("Float32");
                break;
            case "double":
                sqlBuilder.append("Float64");
                break;
            case "string":
                sqlBuilder.append("String");
                break;
            case "boolean":
                sqlBuilder.append("UInt8"); // 在ClickHouse中可以用UInt8表示布尔值，0为假，1为真
                break;
            case "date":
                sqlBuilder.append("Date");
                break;
            case "datetime":
                sqlBuilder.append("DateTime");
                break;
            case "long":
                sqlBuilder.append("Int64");
                break;
            case "json":
                sqlBuilder.append("String");
                break;
            default:
                throw new IllegalArgumentException("不支持的数据类型: " + dataType);
        }
        return sqlBuilder.toString();
    }
}
