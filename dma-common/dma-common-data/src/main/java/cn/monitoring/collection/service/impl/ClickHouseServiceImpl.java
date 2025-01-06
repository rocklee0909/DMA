package cn.monitoring.collection.service.impl;

import cn.monitoring.collection.mapper.ClickHouseMapper;
import cn.monitoring.collection.service.IClickHouseService;
import cn.monitoring.common.datasource.annotation.Slave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClickHouse数据库操作接口
 * @Author liru
 */
@Slave
@Service
public class ClickHouseServiceImpl implements IClickHouseService {

    @Autowired
    private ClickHouseMapper clickHouseMapper;

    // 创建表
    @Override
    public void createNewTable(String tableName, String createTableSql) {
        clickHouseMapper.createTable(tableName, createTableSql);
    }

    // 检查表是否存在
    @Override
    public boolean isTableExists(String tableName) {
        return clickHouseMapper.checkTableExists(tableName) == 1;
    }

    // 检查列是否存在
    @Override
    public boolean isColumnExists(String tableName, String columnName) {
        return clickHouseMapper.checkColumnExists(tableName, columnName) == 1;
    }

    // 添加列
    @Override
    public void addColumnToTable(String tableName, String addColumnSql) {
        clickHouseMapper.addColumn(tableName, addColumnSql);
    }

    // 移除列
    @Override
    public void removeColumnFromTable(String tableName, String removeColumnSql) {
        clickHouseMapper.removeColumn(tableName, removeColumnSql);
    }

    // 获取表的所有列名
    @Override
    public List<String> getTableColumnNames(String tableName) {
        return clickHouseMapper.getColumnNames(tableName);
    }

    // 插入数据
    @Override
    public void insert(String tableName, String insertSql) {
        clickHouseMapper.insert(tableName, insertSql);
    }
}
