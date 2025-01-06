package cn.monitoring.collection.service;

import java.util.List;
import cn.monitoring.collection.domain.DataPoint;

/**
 * 数据点位配置Service接口
 * 
 * @author liru
 * @date 2024-12-20
 */
public interface IDataPointService 
{
    /**
     * 查询数据点位配置
     * 
     * @param pointId 数据点位配置主键
     * @return 数据点位配置
     */
    public DataPoint selectDataPointByPointId(Long pointId);

    /**
     * 查询数据点位配置列表
     * 
     * @param dataPoint 数据点位配置
     * @return 数据点位配置集合
     */
    public List<DataPoint> selectDataPointList(DataPoint dataPoint);

    /**
     * 新增数据点位配置
     * 
     * @param dataPoint 数据点位配置
     * @return 结果
     */
    public int insertDataPoint(DataPoint dataPoint);

    /**
     * 修改数据点位配置
     * 
     * @param dataPoint 数据点位配置
     * @return 结果
     */
    public int updateDataPoint(DataPoint dataPoint);

    /**
     * 批量删除数据点位配置
     * 
     * @param pointIds 需要删除的数据点位配置主键集合
     * @return 结果
     */
    public int deleteDataPointByPointIds(Long[] pointIds);

    /**
     * 删除数据点位配置信息
     * 
     * @param pointId 数据点位配置主键
     * @return 结果
     */
    public int deleteDataPointByPointId(Long pointId);

    /**
     * 根据表名查询数据点位配置
     * @param tableName
     * @return
     */
    List<DataPoint> selectDataPointByTableName(String tableName);
}
