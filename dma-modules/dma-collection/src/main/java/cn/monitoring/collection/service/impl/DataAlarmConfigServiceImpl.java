package cn.monitoring.collection.service.impl;

import java.util.List;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.collection.mapper.DataAlarmConfigMapper;
import cn.monitoring.collection.domain.DataAlarmConfig;
import cn.monitoring.collection.service.IDataAlarmConfigService;

/**
 * 告警配置Service业务层处理
 * 
 * @author liru
 * @date 2025-01-03
 */
@Service
public class DataAlarmConfigServiceImpl implements IDataAlarmConfigService 
{
    @Autowired
    private DataAlarmConfigMapper dataAlarmConfigMapper;

    /**
     * 查询告警配置
     * 
     * @param alertId 告警配置主键
     * @return 告警配置
     */
    @Override
    public DataAlarmConfig selectDataAlarmConfigByAlertId(Long alertId)
    {
        return dataAlarmConfigMapper.selectDataAlarmConfigByAlertId(alertId);
    }

    /**
     * 查询告警配置列表
     * 
     * @param dataAlarmConfig 告警配置
     * @return 告警配置
     */
    @Override
    public List<DataAlarmConfig> selectDataAlarmConfigList(DataAlarmConfig dataAlarmConfig)
    {
        return dataAlarmConfigMapper.selectDataAlarmConfigList(dataAlarmConfig);
    }

    /**
     * 新增告警配置
     * 
     * @param dataAlarmConfig 告警配置
     * @return 结果
     */
    @Override
    public int insertDataAlarmConfig(DataAlarmConfig dataAlarmConfig)
    {
        dataAlarmConfig.setCreateBy(SecurityUtils.getUsername());
        dataAlarmConfig.setCreateTime(DateUtils.getNowDate());
        return dataAlarmConfigMapper.insertDataAlarmConfig(dataAlarmConfig);
    }

    /**
     * 修改告警配置
     * 
     * @param dataAlarmConfig 告警配置
     * @return 结果
     */
    @Override
    public int updateDataAlarmConfig(DataAlarmConfig dataAlarmConfig)
    {
        dataAlarmConfig.setUpdateBy(SecurityUtils.getUsername());
        dataAlarmConfig.setUpdateTime(DateUtils.getNowDate());
        return dataAlarmConfigMapper.updateDataAlarmConfig(dataAlarmConfig);
    }

    /**
     * 批量删除告警配置
     * 
     * @param alertIds 需要删除的告警配置主键
     * @return 结果
     */
    @Override
    public int deleteDataAlarmConfigByAlertIds(Long[] alertIds)
    {
        return dataAlarmConfigMapper.deleteDataAlarmConfigByAlertIds(alertIds);
    }

    /**
     * 删除告警配置信息
     * 
     * @param alertId 告警配置主键
     * @return 结果
     */
    @Override
    public int deleteDataAlarmConfigByAlertId(Long alertId)
    {
        return dataAlarmConfigMapper.deleteDataAlarmConfigByAlertId(alertId);
    }
}
