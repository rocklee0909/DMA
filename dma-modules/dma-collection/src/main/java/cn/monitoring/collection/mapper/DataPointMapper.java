package cn.monitoring.collection.mapper;

import java.util.List;
import cn.monitoring.collection.domain.DataPoint;

/**
 * 数据点位配置Mapper接口
 * 
 * @author liru
 * @date 2024-12-20
 */
public interface DataPointMapper 
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
     * 删除数据点位配置
     * 
     * @param pointId 数据点位配置主键
     * @return 结果
     */
    public int deleteDataPointByPointId(Long pointId);

    /**
     * 批量删除数据点位配置
     * 
     * @param pointIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataPointByPointIds(Long[] pointIds);

    /**
     * 根据表名查询数据点位配置
     * @param tableName
     * @return 结果
     */
    public List<DataPoint> selectDataPointByTableName(String tableName);
}
