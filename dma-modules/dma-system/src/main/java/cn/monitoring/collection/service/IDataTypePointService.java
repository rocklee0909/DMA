package cn.monitoring.collection.service;

import java.util.List;

import cn.monitoring.collection.domain.DataTypePoint;
import cn.monitoring.collection.domain.vo.DataTypePointVo;

/**
 * 数据类型点位关联Service接口
 * 
 * @author liru
 * @date 2024-12-31
 */
public interface IDataTypePointService 
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
     * @param dataTypePointVo 数据类型点位关联Vo
     * @return 结果
     */
    public int insertDataTypePoint(DataTypePointVo dataTypePointVo);

    /**
     * 修改数据类型点位关联
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 结果
     */
    public int updateDataTypePoint(DataTypePoint dataTypePoint);

    /**
     * 批量删除数据类型点位关联
     * 
     * @param typeIds 需要删除的数据类型点位关联主键集合
     * @return 结果
     */
    public int deleteDataTypePointByTypeIds(Long[] typeIds);

    /**
     * 删除数据类型点位关联信息
     * 
     * @param typeId 数据类型点位关联主键
     * @return 结果
     */
    public int deleteDataTypePointByTypeId(Long typeId);

    /**
     * 删除数据点位关联类型信息
     * @param pointIds
     * @return
     */
    int deleteDataTypePointByPointIds(Long[] pointIds);
}
