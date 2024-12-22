package cn.monitoring.collection.service;

import java.util.List;

/**
 * ClickHouse数据库操作服务接口
 */
public interface IClickHouseService
{
    // 创建表
    void createNewTable(String tableName, String createTableSql);

    // 检查表是否存在
    boolean isTableExists(String tableName);

    // 检查列是否存在
    boolean isColumnExists(String tableName, String columnName);

    // 添加列
    void addColumnToTable(String tableName, String addColumnSql);

    // 移除列
    void removeColumnFromTable(String tableName, String removeColumnSql);

    // 获取表的所有列名
    List<String> getTableColumnNames(String tableName);

    // 插入数据
    void insert(String tableName, String insertSql);
}
