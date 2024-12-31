package cn.monitoring.collection.mapper;

import java.util.List;
import cn.monitoring.collection.domain.DataType;
import org.apache.ibatis.annotations.Param;

/**
 * 数据类型配置Mapper接口
 * 
 * @author liru
 * @date 2024-12-30
 */
public interface DataTypeMapper 
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
     * 删除数据类型配置
     * 
     * @param typeId 数据类型配置主键
     * @return 结果
     */
    public int deleteDataTypeByTypeId(Long typeId);

    /**
     * 批量删除数据类型配置
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataTypeByTypeIds(Long[] typeIds);

    /**
     * 查询子节点
     * @param typeId
     * @return
     */
    public List<DataType> selectChildrenDataTypeById(Long typeId);

    /**
     * 批量更新子节点
     * @param dataTypes
     */
    public void updateDataTypeChildren(@Param("dataTypes") List<DataType> dataTypes);
}
