package cn.monitoring.collection.service.impl;

import java.util.List;

import cn.monitoring.common.core.exception.job.TaskException;
import cn.monitoring.common.redis.service.RedisService;
import cn.monitoring.common.security.utils.SecurityUtils;
import cn.monitoring.job.api.RemoteSysJobService;
import cn.monitoring.job.api.domain.SysJob;
import com.alibaba.fastjson.JSON;
import org.quartz.SchedulerException;
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

    @Autowired
    private RemoteSysJobService remoteSysJobService;

    @Autowired
    private RedisService redisService;

    /**
     * 查询告警配置
     * 
     * @param alertId 告警配置主键
     * @return 告警配置
     */
    @Override
    public DataAlarmConfig selectDataAlarmConfigByAlertId(Long alertId)
    {
        DataAlarmConfig dataAlarmConfig = dataAlarmConfigMapper.selectDataAlarmConfigByAlertId(alertId);
        //查询告警执行器
        SysJob sysJob = remoteSysJobService.selectSysJobByJobId(dataAlarmConfig.getSysJob().getJobId()).getData();
        dataAlarmConfig.setSysJob(sysJob);
        return dataAlarmConfig;
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
        List<DataAlarmConfig> dataAlarmConfigs = dataAlarmConfigMapper.selectDataAlarmConfigList(dataAlarmConfig);
        for (DataAlarmConfig dataAlarmConfig1 : dataAlarmConfigs) {
            SysJob sysJob = remoteSysJobService.selectSysJobByJobId(dataAlarmConfig1.getSysJob().getJobId()).getData();
            dataAlarmConfig1.setSysJob(sysJob);
        }
        return dataAlarmConfigs;
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

        int result = dataAlarmConfigMapper.insertDataAlarmConfig(dataAlarmConfig);

        SysJob sysJob = dataAlarmConfig.getSysJob();
        //构造告警执行器
        sysJob.setInvokeTarget("alarmTask.dataCheck("+dataAlarmConfig.getAlertId()+")");
        try {
            remoteSysJobService.insertSysJob(sysJob);
            //添加告警内容到Redis
            redisService.setCacheObject("alarm_"+dataAlarmConfig.getAlertId(), dataAlarmConfig);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }

        return result;
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
        int result = dataAlarmConfigMapper.updateDataAlarmConfig(dataAlarmConfig);

        SysJob sysJob = dataAlarmConfig.getSysJob();
        //构造告警执行器
        sysJob.setInvokeTarget("alarmTask.dataCheck("+dataAlarmConfig.getAlertId()+")");
        try {
            remoteSysJobService.updateSysJob(sysJob);
            //添加告警内容到Redis
            redisService.setCacheObject("alarm_"+dataAlarmConfig.getAlertId(), dataAlarmConfig);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }

        return result;
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
        for (Long alarmId : alertIds) {
            DataAlarmConfig dataAlarmConfig = selectDataAlarmConfigByAlertId(alarmId);
            Long jobId = dataAlarmConfig.getSysJob().getJobId();
            try {
                remoteSysJobService.deleteSysJobByJobId(new Long[]{jobId});
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            } catch (TaskException e) {
                throw new RuntimeException(e);
            }
            redisService.deleteObject("event_"+jobId);
        }

        int result = dataAlarmConfigMapper.deleteDataAlarmConfigByAlertIds(alertIds);
        return result;
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
        DataAlarmConfig dataAlarmConfig = selectDataAlarmConfigByAlertId(alertId);
        Long jobId = dataAlarmConfig.getSysJob().getJobId();
        try {
            remoteSysJobService.deleteSysJobByJobId(new Long[]{jobId});
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }
        redisService.deleteObject("event_"+jobId);

        return dataAlarmConfigMapper.deleteDataAlarmConfigByAlertId(alertId);
    }
}
