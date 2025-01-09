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

    @Autowired
    private RemoteSysJobService remoteSysJobService;

    @Autowired
    private RedisService redisService;

    /**
     * 查询事件配置
     * 
     * @param eventId 事件配置主键
     * @return 事件配置
     */
    @Override
    public DataEventConfig selectDataEventConfigByEventId(Long eventId)
    {
        DataEventConfig dataEventConfig = dataEventConfigMapper.selectDataEventConfigByEventId(eventId);
        //查询事件执行器
        SysJob sysJob = remoteSysJobService.selectSysJobByJobId(dataEventConfig.getSysJob().getJobId()).getData();
        dataEventConfig.setSysJob(sysJob);
        return dataEventConfig;
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
        List<DataEventConfig> dataEventConfigs = dataEventConfigMapper.selectDataEventConfigList(dataEventConfig);
        for (DataEventConfig dataEventConfig1 : dataEventConfigs) {
            SysJob sysJob = remoteSysJobService.selectSysJobByJobId(dataEventConfig1.getSysJob().getJobId()).getData();
            dataEventConfig1.setSysJob(sysJob);
        }
        return dataEventConfigs;
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

        int result = dataEventConfigMapper.insertDataEventConfig(dataEventConfig);

        SysJob sysJob = dataEventConfig.getSysJob();
        //构造事件执行器
        sysJob.setInvokeTarget("eventTask.dataCheck("+dataEventConfig.getEventId()+")");
        try {
            remoteSysJobService.insertSysJob(sysJob);
            //添加事件内容到Redis
            redisService.setCacheObject("event_"+dataEventConfig.getEventId(), dataEventConfig);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }

        return result;
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
        int result = dataEventConfigMapper.updateDataEventConfig(dataEventConfig);

        SysJob sysJob = dataEventConfig.getSysJob();
        //构造事件执行器
        sysJob.setInvokeTarget("eventTask.dataCheck("+dataEventConfig.getEventId()+")");
        try {
            remoteSysJobService.updateSysJob(sysJob);
            //添加事件内容到Redis
            redisService.setCacheObject("event_"+dataEventConfig.getEventId(), dataEventConfig);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }

        return result;
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
        for (Long eventId : eventIds) {
            DataEventConfig dataEventConfig = selectDataEventConfigByEventId(eventId);
            Long jobId = dataEventConfig.getSysJob().getJobId();
            try {
                remoteSysJobService.deleteSysJobByJobId(new Long[]{jobId});
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            } catch (TaskException e) {
                throw new RuntimeException(e);
            }
            redisService.deleteObject("event_"+jobId);
        }

        int result = dataEventConfigMapper.deleteDataEventConfigByEventIds(eventIds);
        return result;
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
        DataEventConfig dataEventConfig = selectDataEventConfigByEventId(eventId);
        Long jobId = dataEventConfig.getSysJob().getJobId();
        try {
            remoteSysJobService.deleteSysJobByJobId(new Long[]{jobId});
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }
        redisService.deleteObject("event_"+jobId);

        return dataEventConfigMapper.deleteDataEventConfigByEventId(eventId);
    }
}
