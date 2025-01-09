package cn.monitoring.job.task;

import cn.monitoring.collection.service.IClickHouseService;
import cn.monitoring.common.redis.service.RedisService;
import cn.monitoring.system.api.RemoteDataEventConfigService;
import cn.monitoring.system.api.domain.RemoteDataEventConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 事件任务调度测试
 * 
 * @author liru
 */
@Component("eventTask")
public class EventTask
{

    @Autowired
    private RedisService redisService;

    @Autowired
    private IClickHouseService clickHouseService;

    @Autowired
    private RemoteDataEventConfigService remoteDataEventConfigService;

    public void dataCheck(Long eventId)
    {
        System.out.println("事件数据检查。。。。"+eventId);
        System.out.println("eventJsonObject:"+redisService.getCacheObject("event_"+eventId));

        // 从 Redis 中获取 RemoteDataEventConfig 对象
        RemoteDataEventConfig dataEventConfig = redisService.getCacheObject("event_" + eventId);
        if (dataEventConfig == null) {
            System.out.println("未找到事件配置，事件ID：" + eventId);
            return;
        }

        // 解析触发条件公式 dataEventConfig.getTriggerCondition()
        String triggerCondition = dataEventConfig.getTriggerCondition();
        // 这里需要根据具体的触发条件公式进行解析和处理
        // 假设触发条件公式是一个简单的 SQL 语句，我们可以使用该语句从 ClickHouse 中查询数据
        // 以下是一个简单示例，实际情况中你可能需要更复杂的逻辑来解析和处理不同类型的触发条件
        String query = triggerCondition;
        // 从 ClickHouse 中查询对应公式中对应设备的数据库的最新记录集合
        // 这里假设 IClickHouseService 提供了一个方法来执行查询操作并返回结果集
//        Object resultSet = clickHouseService.executeQuery(query);

        // 判断是否触发事件，假设根据结果集的某些条件来判断
        boolean shouldTrigger = false;
        // 这里需要根据具体的业务逻辑判断是否触发事件，例如根据结果集的大小、数据的某些属性等
//        if (resultSet!= null) {
//            shouldTrigger = true;
//        }


        if (shouldTrigger) {
            // 触发事件，发送邮件，短信，钉钉，微信
            // 这里假设存在一些服务类来发送不同类型的通知，以下是示例代码，需要根据实际情况添加具体的实现
//            sendEmailNotification(eventId);
//            sendSmsNotification(eventId);
//            sendDingTalkNotification(eventId);
//            sendWeChatNotification(eventId);


            // 记录事件日志
            //logEvent(eventId, "事件触发，满足触发条件：" + triggerCondition);


            // 更新事件最新序列号，假设最新序列号更新为当前时间的毫秒值
            dataEventConfig.setLatestSerial(System.currentTimeMillis());


            // 更新事件检查时间为当前时间
            dataEventConfig.setCheckTime(new Date());


            // 更新事件检查设备数量，假设根据结果集的大小更新
            // 这里需要根据具体的结果集进行计算，以下是一个简单示例
            dataEventConfig.setCheckEquipmentNum(10);


            // 更新事件检查频率，假设检查频率加 1
            dataEventConfig.setCheckRate(dataEventConfig.getCheckRate() + 1);

            // 将更新后的 RemoteDataEventConfig 存储回 Redis 或持久化到数据库
            // 这里假设使用 remoteDataEventConfigService 来更新配置
            remoteDataEventConfigService.update(dataEventConfig);

        } else {
            // 记录事件日志
            logEvent(eventId, "事件未触发，不满足触发条件：" + triggerCondition);
        }
    }


    private void sendEmailNotification(Long eventId) {
        // 实现发送邮件的逻辑，这里可以使用 JavaMailSender 或其他邮件发送库
        System.out.println("发送邮件通知，事件 ID：" + eventId);
    }


    private void sendSmsNotification(Long eventId) {
        // 实现发送短信的逻辑，这里可以使用短信服务提供商的 API 或短信服务中间件
        System.out.println("发送短信通知，事件 ID：" + eventId);
    }


    private void sendDingTalkNotification(Long eventId) {
        // 实现发送钉钉消息的逻辑，这里可以使用钉钉开放平台的 API
        System.out.println("发送钉钉消息通知，事件 ID：" + eventId);
    }


    private void sendWeChatNotification(Long eventId) {
        // 实现发送微信消息的逻辑，这里可以使用微信开放平台的 API 或企业微信 API
        System.out.println("发送微信消息通知，事件 ID：" + eventId);
    }


    private void logEvent(Long eventId, String message) {
        // 实现记录事件日志的逻辑，这里可以将日志存储在文件、数据库或日志服务中
        System.out.println("事件日志：" + eventId + " - " + message);
    }
}
