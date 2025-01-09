package cn.monitoring.job.task;

import cn.monitoring.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 告警任务调度测试
 * 
 * @author liru
 */
@Component("alarmTask")
public class AlarmTask
{

    @Autowired
    private RedisService redisService;

    public void dataCheck(Long alramId)
    {
        System.out.println("告警数据检查。。。。"+alramId);
        System.out.println("alarmJsonObject:"+redisService.getCacheObject("event_"+alramId));
    }
}
