package cn.monitoring.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * 手动绑定消费者接口
 * @author liru
 */
public interface IManualBindConsumer {
    /**
     * 消费监听处理消息的方法
     * 目前kafka配置为手动提交，所以需要手动ack
     * @param message
     * @param ack
     */
    void consumerMessage(List<ConsumerRecord<String, Object>> message, Acknowledgment ack);
}
