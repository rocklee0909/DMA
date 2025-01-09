package cn.monitoring.job.api.sysJob;

import cn.monitoring.common.core.domain.R;
import cn.monitoring.common.core.exception.job.TaskException;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.job.api.RemoteSysJobService;
import cn.monitoring.job.api.domain.SysJob;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 定时任务服务降级处理
 * 
 * @author liru
 */
@Component
public class RemoteSysJobFallbackFactory implements FallbackFactory<RemoteSysJobService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteSysJobFallbackFactory.class);

    @Override
    public RemoteSysJobService create(Throwable throwable)
    {
        log.error("定时任务服务调用失败:{}", throwable.getMessage());
        return new RemoteSysJobService()
        {
            @Override
            public R<SysJob> insertSysJob(SysJob job) throws SchedulerException, TaskException {
                return R.fail("插入定时任务失败:" + throwable.getMessage());
            }

            @Override
            public R<SysJob> updateSysJob(SysJob job) throws SchedulerException, TaskException {
                return R.fail("修改定时任务失败:" + throwable.getMessage());
            }

            @Override
            public R<Integer> deleteSysJobByJobId(Long[] jobIds) throws SchedulerException, TaskException {
                return R.fail("删除定时任务失败:" + throwable.getMessage());
            }

            @Override
            public R<SysJob> selectSysJobByJobId(Long jobId) {
                return R.fail("查询定时任务失败:" + throwable.getMessage());
            }
        };
    }
}
