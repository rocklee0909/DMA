package cn.monitoring.collection.service.impl;

import java.util.Collections;
import java.util.List;

import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.domain.vo.TreeSelect;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.collection.mapper.DataTypePointMapper;
import cn.monitoring.collection.domain.DataTypePoint;
import cn.monitoring.collection.service.IDataTypePointService;

/**
 * 数据类型点位关联Service业务层处理
 * 
 * @author liru
 * @date 2024-12-31
 */
@Service
public class DataTypePointServiceImpl implements IDataTypePointService 
{
    @Autowired
    private DataTypePointMapper dataTypePointMapper;

    /**
     * 查询数据类型点位关联
     * 
     * @param typeId 数据类型点位关联主键
     * @return 数据类型点位关联
     */
    @Override
    public DataTypePoint selectDataTypePointByTypeId(Long typeId)
    {
        return dataTypePointMapper.selectDataTypePointByTypeId(typeId);
    }

    /**
     * 查询数据类型点位关联列表
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 数据类型点位关联
     */
    @Override
    public List<DataTypePoint> selectDataTypePointList(DataTypePoint dataTypePoint)
    {
        return dataTypePointMapper.selectDataTypePointList(dataTypePoint);
    }

    /**
     * 新增数据类型点位关联
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 结果
     */
    @Override
    public int insertDataTypePoint(DataTypePoint dataTypePoint)
    {
        dataTypePoint.setCreateBy(SecurityUtils.getUsername());
        dataTypePoint.setCreateTime(DateUtils.getNowDate());
        return dataTypePointMapper.insertDataTypePoint(dataTypePoint);
    }

    /**
     * 修改数据类型点位关联
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 结果
     */
    @Override
    public int updateDataTypePoint(DataTypePoint dataTypePoint)
    {
        dataTypePoint.setUpdateBy(SecurityUtils.getUsername());
        dataTypePoint.setUpdateTime(DateUtils.getNowDate());
        return dataTypePointMapper.updateDataTypePoint(dataTypePoint);
    }

    /**
     * 批量删除数据类型点位关联
     * 
     * @param typeIds 需要删除的数据类型点位关联主键
     * @return 结果
     */
    @Override
    public int deleteDataTypePointByTypeIds(Long[] typeIds)
    {
        return dataTypePointMapper.deleteDataTypePointByTypeIds(typeIds);
    }

    /**
     * 删除数据类型点位关联信息
     * 
     * @param typeId 数据类型点位关联主键
     * @return 结果
     */
    @Override
    public int deleteDataTypePointByTypeId(Long typeId)
    {
        return dataTypePointMapper.deleteDataTypePointByTypeId(typeId);
    }

}
