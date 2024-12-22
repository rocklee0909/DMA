package cn.monitoring.collection.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClickHouse数据库操作接口
 * @Author liru
 * @Date 2024-12-19
 */
public interface ClickHouseMapper {

    /**
     * 创建表
     *
     * @param tableName 表名
     * @param createTableSql 创建表的SQL语句
     */
    public void createTable(@Param("tableName") String tableName, @Param("createTableSql") String createTableSql);

    /**
     * 检查表是否存在
     *
     * @param tableName 表名
     * @return 存在返回1，不存在返回0
     */
    public Integer checkTableExists(@Param("tableName") String tableName);

    /**
     * 检查列是否存在
     *
     * @param tableName 表名
     * @param columnName 列名
     * @return 存在返回1，不存在返回0
     */
    public Integer checkColumnExists(@Param("tableName") String tableName, @Param("columnName") String columnName);

    /**
     * 添加列到表中
     *
     * @param tableName 表名
     * @param addColumnSql 添加列的SQL语句
     */
    public Integer addColumn(@Param("tableName") String tableName, @Param("addColumnSql") String addColumnSql);

    /**
     * 移除表中的列
     *
     * @param tableName 表名
     * @param removeColumnSql 移除列的SQL语句
     */
    public void removeColumn(@Param("tableName") String tableName, @Param("removeColumnSql") String removeColumnSql);

    /**
     * 获取表的所有列名
     *
     * @param tableName 表名
     * @return 列名列表
     */
    public List<String> getColumnNames(@Param("tableName") String tableName);

    /**
     * 插入数据
     * @param tableName
     * @param insertSql
     */
    public Integer insert(@Param("tableName") String tableName, @Param("insertSql") String insertSql);
}