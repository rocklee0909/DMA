package cn.monitoring.collection.service.impl;

import java.util.List;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.collection.mapper.DataEventConfigMapper;
import cn.monitoring.collection.domain.DataEventConfig;
import cn.monitoring.collection.service.IDataEventConfigService;

/**
 * 事件配置Service业务层处理
 * 
 * @author liru
 * @date 2025-01-03
 */
@Service
public class DataEventConfigServiceImpl implements IDataEventConfigService 
{
    @Autowired
    private DataEventConfigMapper dataEventConfigMapper;

    /**
     * 查询事件配置
     * 
     * @param eventId 事件配置主键
     * @return 事件配置
     */
    @Override
    public DataEventConfig selectDataEventConfigByEventId(Long eventId)
    {
        return dataEventConfigMapper.selectDataEventConfigByEventId(eventId);
    }

    /**
     * 查询事件配置列表
     * 
     * @param dataEventConfig 事件配置
     * @return 事件配置
     */
    @Override
    public List<DataEventConfig> selectDataEventConfigList(DataEventConfig dataEventConfig)
    {
        return dataEventConfigMapper.selectDataEventConfigList(dataEventConfig);
    }

    /**
     * 新增事件配置
     * 
     * @param dataEventConfig 事件配置
     * @return 结果
     */
    @Override
    public int insertDataEventConfig(DataEventConfig dataEventConfig)
    {
        dataEventConfig.setCreateBy(SecurityUtils.getUsername());
        dataEventConfig.setCreateTime(DateUtils.getNowDate());
        return dataEventConfigMapper.insertDataEventConfig(dataEventConfig);
    }

    /**
     * 修改事件配置
     * 
     * @param dataEventConfig 事件配置
     * @return 结果
     */
    @Override
    public int updateDataEventConfig(DataEventConfig dataEventConfig)
    {
        dataEventConfig.setUpdateBy(SecurityUtils.getUsername());
        dataEventConfig.setUpdateTime(DateUtils.getNowDate());
        return dataEventConfigMapper.updateDataEventConfig(dataEventConfig);
    }

    /**
     * 批量删除事件配置
     * 
     * @param eventIds 需要删除的事件配置主键
     * @return 结果
     */
    @Override
    public int deleteDataEventConfigByEventIds(Long[] eventIds)
    {
        return dataEventConfigMapper.deleteDataEventConfigByEventIds(eventIds);
    }

    /**
     * 删除事件配置信息
     * 
     * @param eventId 事件配置主键
     * @return 结果
     */
    @Override
    public int deleteDataEventConfigByEventId(Long eventId)
    {
        return dataEventConfigMapper.deleteDataEventConfigByEventId(eventId);
    }
}
