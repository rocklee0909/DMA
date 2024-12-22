package cn.monitoring.common.kafka.controller;

import cn.monitoring.common.kafka.utils.KafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 *@ClassName KakfaTest
 *@Description:
 *@Date 2024/5/16 15:21
 **/

@RestController
@RequestMapping("/kafkatest")
public class KafkaTest {

    @Autowired
    private KafkaUtil kafkaUtil;

    @RequestMapping("/kafkatest")
    public void test() {

        try {
            String topicName = "kafka-test-1";
            kafkaUtil.createTopic(topicName, 1, (short) 1);//创建topic
            kafkaUtil.updateTopicRetention(topicName, String.valueOf(1000000));//更新topic的过期时间

            Set<String> strings = kafkaUtil.listTopics();//查出所有topic
            System.out.println("所有topic:" + strings);

            boolean b = kafkaUtil.existTopic(topicName);//查询topic是否存在
            System.out.println("topic-是否存在：" + b);


            String listenerID = "kafka-test-listener-1";

            //创建监听容器
            kafkaUtil.registerListenerContainer(listenerID, "test-consumer-group", new KafkaTest(), KafkaTest.class.getDeclaredMethod("consumerMessage",List.class,Acknowledgment.class), topicName);

            boolean b1 = kafkaUtil.existListenerContainer(listenerID);//查询监听容器是否存在
            System.out.println("容器-是否存在：" + b1);

            boolean normalStateListenerContainer = kafkaUtil.isNormalStateListenerContainer(listenerID);//查询监听容器是否为正常状态
            System.out.println("容器-状态：" + normalStateListenerContainer);

            kafkaUtil.pauseListenerContainer(listenerID);//暂停监听容器的监听状态
            boolean pauseStateListenerContainer = kafkaUtil.getPauseStateListenerContainer(listenerID);//查询监听容器的监听状态
            System.out.println("容器-监听状态：" + !pauseStateListenerContainer);


            kafkaUtil.stopListenerContainer(listenerID);//暂停监听容器的监听状态
            boolean runningStateListenerContainer = kafkaUtil.getRunningStateListenerContainer(listenerID);//查询监听容器的监听状态
            System.out.println("容器-运行状态：" + runningStateListenerContainer);


            boolean normalStateListenerContainer2 = kafkaUtil.isNormalStateListenerContainer(listenerID);//查询监听容器是否为正常状态
            System.out.println("容器-状态：" + normalStateListenerContainer2);


            boolean b2 = kafkaUtil.setStateNormalListenerContainer(listenerID);//设置监听容器为正常状态
            boolean normalStateListenerContainer3 = kafkaUtil.isNormalStateListenerContainer(listenerID);//查询监听容器是否为正常状态
            System.out.println("容器-状态：" + normalStateListenerContainer3);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping("/del")
    public void deleteTopic() throws Exception {
        String topicName = "kafka-test-1";
        kafkaUtil.deleteTopic(topicName);//删除topic
    }


    @RequestMapping("/send")
    public void sendMsg() throws Exception {
        String topicName = "kafka-test-1";
        kafkaUtil.sendMsg(topicName, "haha");
        boolean b = kafkaUtil.existTopic(topicName);//查询topic是否存在
        System.out.println("topic-是否存在：" + b);
    }


    /**
     * @param message 接受来自kakfa的参数
     * @param ack     消息确认参数
     * @return void
     * @Title consumerMessage
     * @Description TODO 消费监听处理消息的方法
     */
    public void consumerMessage(List<ConsumerRecord<String, Object>> message, Acknowledgment ack) {
        System.out.println("收到消息：" + message);

        System.out.println(kafkaUtil);
        //消息确认
        ack.acknowledge();
    }
}