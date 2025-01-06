package cn.monitoring.collection.service;

import java.util.List;
import cn.monitoring.collection.domain.DataType;

/**
 * 数据类型配置Service接口
 * 
 * @author liru
 * @date 2024-12-30
 */
public interface IDataTypeService 
{
    /**
     * 查询数据类型配置
     * 
     * @param typeId 数据类型配置主键
     * @return 数据类型配置
     */
    public DataType selectDataTypeByTypeId(Long typeId);

    /**
     * 查询数据类型配置列表
     * 
     * @param dataType 数据类型配置
     * @return 数据类型配置集合
     */
    public List<DataType> selectDataTypeList(DataType dataType);

    /**
     * 新增数据类型配置
     * 
     * @param dataType 数据类型配置
     * @return 结果
     */
    public int insertDataType(DataType dataType);

    /**
     * 修改数据类型配置
     * 
     * @param dataType 数据类型配置
     * @return 结果
     */
    public int updateDataType(DataType dataType);

    /**
     * 批量删除数据类型配置
     * 
     * @param typeIds 需要删除的数据类型配置主键集合
     * @return 结果
     */
    public int deleteDataTypeByTypeIds(Long[] typeIds);

    /**
     * 删除数据类型配置信息
     * 
     * @param typeId 数据类型配置主键
     * @return 结果
     */
    public int deleteDataTypeByTypeId(Long typeId);
}
