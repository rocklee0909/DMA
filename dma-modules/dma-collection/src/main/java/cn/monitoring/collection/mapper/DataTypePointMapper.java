package cn.monitoring.collection.mapper;

import java.util.List;
import cn.monitoring.collection.domain.DataTypePoint;

/**
 * 数据类型点位关联Mapper接口
 * 
 * @author liru
 * @date 2024-12-31
 */
public interface DataTypePointMapper 
{
    /**
     * 查询数据类型点位关联
     * 
     * @param typeId 数据类型点位关联主键
     * @return 数据类型点位关联
     */
    public DataTypePoint selectDataTypePointByTypeId(Long typeId);

    /**
     * 查询数据类型点位关联列表
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 数据类型点位关联集合
     */
    public List<DataTypePoint> selectDataTypePointList(DataTypePoint dataTypePoint);

    /**
     * 新增数据类型点位关联
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 结果
     */
    public int insertDataTypePoint(DataTypePoint dataTypePoint);

    /**
     * 修改数据类型点位关联
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 结果
     */
    public int updateDataTypePoint(DataTypePoint dataTypePoint);

    /**
     * 删除数据类型点位关联
     * 
     * @param typeId 数据类型点位关联主键
     * @return 结果
     */
    public int deleteDataTypePointByTypeId(Long typeId);

    /**
     * 批量删除数据类型点位关联
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataTypePointByTypeIds(Long[] typeIds);

    /**
     * 查询类型关联的点位数量
     * @param typeId 类型id
     * @return
     */
    Integer countByTypeId(Long typeId);

    /**
     * 删除数据点位关联类型信息
     * @param pointIds
     * @return
     */
    int deleteDataTypePointByPointIds(Long[] pointIds);
}
