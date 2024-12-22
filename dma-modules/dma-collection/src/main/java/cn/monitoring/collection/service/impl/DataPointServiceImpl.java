package cn.monitoring.collection.service.impl;

import java.util.Collections;
import java.util.List;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.collection.mapper.DataPointMapper;
import cn.monitoring.collection.domain.DataPoint;
import cn.monitoring.collection.service.IDataPointService;

/**
 * 数据点位配置Service业务层处理
 * 
 * @author liru
 * @date 2024-12-20
 */
@Service
public class DataPointServiceImpl implements IDataPointService 
{
    @Autowired
    private DataPointMapper dataPointMapper;

    /**
     * 查询数据点位配置
     * 
     * @param pointId 数据点位配置主键
     * @return 数据点位配置
     */
    @Override
    public DataPoint selectDataPointByPointId(Long pointId)
    {
        return dataPointMapper.selectDataPointByPointId(pointId);
    }

    /**
     * 查询数据点位配置列表
     * 
     * @param dataPoint 数据点位配置
     * @return 数据点位配置
     */
    @Override
    public List<DataPoint> selectDataPointList(DataPoint dataPoint)
    {
        return dataPointMapper.selectDataPointList(dataPoint);
    }

    /**
     * 新增数据点位配置
     * 
     * @param dataPoint 数据点位配置
     * @return 结果
     */
    @Override
    public int insertDataPoint(DataPoint dataPoint)
    {
        dataPoint.setCreateTime(DateUtils.getNowDate());
        return dataPointMapper.insertDataPoint(dataPoint);
    }

    /**
     * 修改数据点位配置
     * 
     * @param dataPoint 数据点位配置
     * @return 结果
     */
    @Override
    public int updateDataPoint(DataPoint dataPoint)
    {
        dataPoint.setUpdateBy(SecurityUtils.getUsername());
        dataPoint.setUpdateTime(DateUtils.getNowDate());
        return dataPointMapper.updateDataPoint(dataPoint);
    }

    /**
     * 批量删除数据点位配置
     * 
     * @param pointIds 需要删除的数据点位配置主键
     * @return 结果
     */
    @Override
    public int deleteDataPointByPointIds(Long[] pointIds)
    {
        return dataPointMapper.deleteDataPointByPointIds(pointIds);
    }

    /**
     * 删除数据点位配置信息
     * 
     * @param pointId 数据点位配置主键
     * @return 结果
     */
    @Override
    public int deleteDataPointByPointId(Long pointId)
    {
        return dataPointMapper.deleteDataPointByPointId(pointId);
    }

    /**
     * 根据表名查询数据点位配置
     * @param tableName
     * @return
     */
    @Override
    public List<DataPoint> selectDataPointByTableName(String tableName) {
        return dataPointMapper.selectDataPointByTableName(tableName);
    }
}
