package cn.monitoring.collection.service.impl;

import java.util.Collections;
import java.util.List;

import cn.monitoring.collection.jobs.DmaKafkaCollectorDataJob;
import cn.monitoring.collection.mapper.CollectorInfoMapper;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.core.utils.SpringUtils;
import cn.monitoring.common.flink.job.FlinkJobManager;
import cn.monitoring.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.service.ICollectorInfoService;

/**
 * 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等Service业务层处理
 * 
 * @author liru
 * @date 2024-12-15
 */
@Slf4j
@Service
public class CollectorInfoServiceImpl implements ICollectorInfoService 
{
    @Autowired
    private CollectorInfoMapper collectorInfoMapper;

    @Autowired
    private FlinkJobManager flinkJobManager;

    /**
     * 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorId 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等主键
     * @return 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     */
    @Override
    public CollectorInfo selectCollectorInfoByCollectorId(Long collectorId)
    {
        return collectorInfoMapper.selectCollectorInfoByCollectorId(collectorId);
    }

    /**
     * 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等列表
     * 
     * @param collectorInfo 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * @return 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     */
    @Override
    public List<CollectorInfo> selectCollectorInfoList(CollectorInfo collectorInfo)
    {
        return collectorInfoMapper.selectCollectorInfoList(collectorInfo);
    }

    /**
     * 新增采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorInfo 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * @return 结果
     */
    @Override
    public int insertCollectorInfo(CollectorInfo collectorInfo)
    {
        collectorInfo.setCreateBy(SecurityUtils.getUsername());
        collectorInfo.setCreateTime(DateUtils.getNowDate());
        int count = collectorInfoMapper.insertCollectorInfo(collectorInfo);
        if(count>0){
            addJob(collectorInfo);
        }
        return count;
    }

    /**
     * 修改采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorInfo 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * @return 结果
     */
    @Override
    public int updateCollectorInfo(CollectorInfo collectorInfo)
    {
        collectorInfo.setUpdateBy(SecurityUtils.getUsername());
        collectorInfo.setUpdateTime(DateUtils.getNowDate());

        int count = collectorInfoMapper.updateCollectorInfo(collectorInfo);
        if(count>0){
            addJob(collectorInfo);
        }
        return count;
    }

    /**
     * 批量删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorIds 需要删除的采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等主键
     * @return 结果
     */
    @Override
    public int deleteCollectorInfoByCollectorIds(Long[] collectorIds)
    {
        return collectorInfoMapper.deleteCollectorInfoByCollectorIds(collectorIds);
    }

    /**
     * 删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等信息
     * 
     * @param collectorId 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等主键
     * @return 结果
     */
    @Override
    public int deleteCollectorInfoByCollectorId(Long collectorId)
    {
        return collectorInfoMapper.deleteCollectorInfoByCollectorId(collectorId);
    }

    @Override
    public void addJob(CollectorInfo collectorInfo){
        Thread thread = new Thread(() -> {
            try {
                DmaKafkaCollectorDataJob job = SpringUtils.getBean(DmaKafkaCollectorDataJob.class);
                job.setJobName(collectorInfo.getCollectorName() + " 采集器数据采集");
                job.setGroupId(collectorInfo.getDmaGroup());
                job.setTopic(collectorInfo.getDmaTopic());
                flinkJobManager.executeJob(job);
            } catch (Exception e) {
                log.error("Error executing Flink job", e);
            }
        });
        thread.start();
    }

    @Override
    public List<CollectorInfo> selectCollectorInfoByDmaTopic(String topic) {
        return collectorInfoMapper.selectCollectorInfoByDmaTopic(topic);
    }
}
