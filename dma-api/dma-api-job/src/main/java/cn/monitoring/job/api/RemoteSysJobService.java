package cn.monitoring.job.api;

import cn.monitoring.common.core.constant.ServiceNameConstants;
import cn.monitoring.common.core.domain.R;
import cn.monitoring.common.core.exception.job.TaskException;
import cn.monitoring.job.api.domain.SysJob;
import cn.monitoring.job.api.sysJob.RemoteSysJobFallbackFactory;
import org.quartz.SchedulerException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务服务
 * 
 * @author liru
 */
@FeignClient(contextId = "remoteSysJobService", value = ServiceNameConstants.JOB_SERVICE, fallbackFactory = RemoteSysJobFallbackFactory.class)
public interface RemoteSysJobService
{

    /**
     * 插入定时任务
     */
    @PostMapping("/insertSysJob")
    public R<SysJob> insertSysJob(@RequestBody SysJob job) throws SchedulerException, TaskException;

    /**
     * 修改定时任务
     */
    @PutMapping("updateSysJob")
    public R<SysJob> updateSysJob(@RequestBody SysJob job) throws SchedulerException, TaskException;


    /**
     * 删除定时任务
     */
    @DeleteMapping("/deleteSysJobByJobId")
    public R<Integer> deleteSysJobByJobId(Long[] jobIds) throws SchedulerException, TaskException;

    /**
     * 查询定时任务
     * @param jobId
     * @return
     */
    @GetMapping(value = "/selectSysJobByJobId")
    public R<SysJob> selectSysJobByJobId(Long jobId);
}
