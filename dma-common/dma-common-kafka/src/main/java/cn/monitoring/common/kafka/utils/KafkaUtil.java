package cn.monitoring.common.kafka.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.monitoring.common.kafka.consumer.IManualBindConsumer;
import cn.monitoring.common.kafka.controller.KafkaTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @Author: liru
 * @ClassName: KafkaUtil
 * @Description: 用于创建kafka Topic队列和listener监听容器的工具类
 **/
@Slf4j
@Component
public class KafkaUtil {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private KafkaListenerContainerFactory<? extends MessageListenerContainer> kafkaListenerContainerFactory;

    @Autowired
    private KafkaAdminClient adminClient;

    /**
     * 创建Kafka主题
     *
     * @param topicName  主题名称
     * @param partitions 分区数
     * @param replicas   副本数
     * @throws Exception 如果创建主题失败
     */
    public void createTopic(String topicName, int partitions, short replicas) throws Exception {
        NewTopic newTopic = new NewTopic(topicName, partitions, replicas);
        CreateTopicsResult topics = adminClient.createTopics(Collections.singleton(newTopic));
        topics.all().get();
        log.info("【{}】topic创建成功", topicName);
    }

    /**
     * 删除Kafka主题
     *
     * @param topicName 主题名称
     * @throws Exception 如果删除主题失败
     */
    public void deleteTopic(String topicName) throws Exception {
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singleton(topicName));
        deleteTopicsResult.all().get();
        log.info("【{}】topic删除成功", topicName);
    }

    /**
     * 更新Kafka主题的过期时间
     *
     * @param topicName 主题名称
     * @param ms        过期时间（毫秒）
     * @throws Exception 如果更新主题过期时间失败
     */
    public void updateTopicRetention(String topicName, String ms) throws Exception {
        ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, topicName);
        ConfigEntry configEntry = new ConfigEntry(TopicConfig.RETENTION_MS_CONFIG, ms);
        Config config = new Config(Collections.singleton(configEntry));
        // 创建AlterConfigsOptions
        AlterConfigsOptions alterConfigsOptions = new AlterConfigsOptions().timeoutMs(10000);
        // 执行修改操作
        adminClient.alterConfigs(Collections.singletonMap(resource, config), alterConfigsOptions).all().get();
        log.info("【{}】topic过期时间设置完成，过期时间为：{}毫秒", topicName, ms);
    }

    /**
     * 获取Kafka主题列表
     *
     * @return 主题列表
     * @throws Exception 如果获取主题列表失败
     */
    public Set<String> listTopics() throws Exception {
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        Set<String> strings = listTopicsResult.names().get();
        return strings;
    }

    /**
     * 检查Kafka主题是否存在
     *
     * @param topicName 主题名称
     * @return true如果主题存在，false否则
     * @throws Exception 如果检查主题存在失败
     */
    public boolean existTopic(String topicName) throws Exception {
        Set<String> strings = listTopics();
        if (strings == null || strings.isEmpty()) {
            return false;
        }
        return strings.contains(topicName);
    }

    /**
     * 发送消息到Kafka主题
     *
     * @param topic 主题名称
     * @param msg   消息内容
     * @throws Exception 如果发送消息失败
     */
    public void sendMsg(String topic, Object msg) throws Exception {
        kafkaTemplate.send(topic, msg);
        //kafkaTemplate.send(topic,2,"key",msg);//带有分区和key值的
    }

    /**
     * 检查监听器容器是否存在
     *
     * @param id 容器ID
     * @return true如果容器存在，false否则
     * @throws Exception 如果检查容器存在失败
     */
    public boolean existListenerContainer(String id) throws Exception {
        Set<String> listenerIds = kafkaListenerEndpointRegistry.getListenerContainerIds();
        return listenerIds.contains(id);
    }

    /**
     * 注册监听器容器
     *
     * @param id              容器ID
     * @param consumerGroupId 消费者组ID
     * @param processBean     处理消息的Bean
     * @param processMethod   处理消息的方法
     * @param topics          监听的主题列表
     * @throws Exception 如果注册容器失败
     */
    public void registerListenerContainer(String id, String consumerGroupId, Object processBean, Method processMethod, String... topics) throws Exception {
        //判断id是否存在
        if (existListenerContainer(id)) {
            //如果当前id的容器已存在，不添加
            log.info("当前id为{}的容器已存在，不进行添加操作！", id);
            return;
        }
        //判断所有队列是否存在
        for (String topic : topics) {
            if (!existTopic(topic)) {
                //如果存在topic不存在，不添加
                log.info("【{}】topic不存在，不进行添加操作！", topic);
                return;
            }
        }
        MethodKafkaListenerEndpoint<String, String> endpoint = new MethodKafkaListenerEndpoint<>();
        //设置监听器端点相关信息
        //设置Id
        endpoint.setId(id);
        //设置消费者组
        endpoint.setGroupId(consumerGroupId);
        //设置要监听的topic数组，可以是多个
        endpoint.setTopics(topics);
        //设置每个监听器线程数
        endpoint.setConcurrency(3);
        //设置批量监听
        endpoint.setBatchListener(true);
        //设置消息处理工厂类，这里用的是默认工厂
        endpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        //设置实际处理的Bean对象，即实际的对象，比如new Class();
        endpoint.setBean(processBean);
        //设置实际处理的方法(包含方法名和参数)
        endpoint.setMethod(processMethod);
        //注册Container并启动，startImmediately表示立马启动
        kafkaListenerEndpointRegistry.registerListenerContainer(endpoint, SpringUtil.getBean(KafkaListenerContainerFactory.class), true);
        log.info("Kafka监听容器操作：ID为{}的容器已【注册】，监听的topics：{}", id, topics);


//        for (String topicName : topics) {
//            if (!KafkaConfig.notExistTopicCreateContainerFlag && !nameTopics.contains(topicName)) {
//                log.info("【{}】topic不存在，不创建容器！", topicName);
//                continue;
//            }
//            //创建一个kafka监听器端点对象
//            MethodKafkaListenerEndpoint<String, String> endpoint = new MethodKafkaListenerEndpoint<>();
//            //设置监听器端点相关信息
//            //设置Id
//            endpoint.setId(topicName);
//            //设置消费者组
//            endpoint.setGroupId(topicName + "_consumer_group");
//            //设置主题
//            endpoint.setTopics(topicName);
//            //设置每个监听器线程数
//            endpoint.setConcurrency(3);
//            //设置批量监听
//            endpoint.setBatchListener(true);
//            //设置默认处理工厂
//            endpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
//            //设置实际处理的Bean对象
//            endpoint.setBean(new ConsumerController());
//            //设置实际处理的方法名和参数类型
//            endpoint.setMethod(ConsumerController.class.getMethod("consumeMessage", String.class));
//            //注册Container并启动
//            kafkaListenerEndpointRegistry.registerListenerContainer(endpoint, SpringUtil.getBean(KafkaListenerContainerFactory.class), true);
//            log.info("Kafka监听容器操作：ID为{}的容器已【注册】", topicName);
//        }
    }

    /**
     * 启动监听器容器
     *
     * @param id 容器ID
     * @throws Exception 如果启动容器失败
     */
    public void startListenerContainer(String id) throws Exception {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        if (listenerContainer == null) {
            log.info("Kafka监听容器操作：ID为{}的容器不存在，不操作！", id);
            return;
        }
        listenerContainer.start();
        log.info("Kafka监听容器操作：ID为{}的容器已【开启】", id);
    }

    /**
     * 停止监听器容器
     *
     * @param id 容器ID
     * @throws Exception 如果停止容器失败
     */
    public void stopListenerContainer(String id) throws Exception {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        if (listenerContainer == null) {
            log.info("Kafka监听容器操作：ID为{}的容器不存在，不操作！", id);
            return;
        }
        listenerContainer.stop();
        log.info("Kafka监听容器操作：ID为{}的容器已【停止】", id);
    }

    /**
     * 暂停监听器容器
     *
     * @param id 容器ID
     * @throws Exception 如果暂停容器失败
     */
    public void pauseListenerContainer(String id) throws Exception {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        if (listenerContainer == null) {
            log.info("Kafka监听容器操作：ID为{}的容器不存在，不操作！", id);
            return;
        }
        listenerContainer.pause();
        log.info("Kafka监听容器操作：ID为{}的容器已【暂停】", id);
    }

    /**
     * 恢复监听器容器
     *
     * @param id 容器ID
     * @throws Exception 如果恢复容器失败
     */
    public void resumeListenerContainer(String id) throws Exception {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        if (listenerContainer == null) {
            log.info("Kafka监听容器操作：ID为{}的容器不存在，不操作！", id);
            return;
        }
        listenerContainer.resume();
        log.info("Kafka监听容器操作：ID为{}的容器已【恢复】", id);
    }

    /**
     * 检查监听器容器是否处于正常状态
     *
     * @param id 容器ID
     * @return true如果容器处于正常状态，false否则
     * @throws Exception 如果检查容器状态失败
     */
    public boolean isNormalStateListenerContainer(String id) throws Exception {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        //如果不存在此id容器，则返回false
        if (listenerContainer == null) {
            return false;
        }
        //存在则返回容器的运行状态和非暂停状态
        return listenerContainer.isRunning() && !listenerContainer.isPauseRequested();
    }

    /**
     * 获取监听器容器的暂停状态
     *
     * @param id 容器ID
     * @return true如果容器被暂停，false否则
     * @throws Exception 如果获取容器暂停状态失败
     */
    public boolean getPauseStateListenerContainer(String id) throws Exception {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        if (listenerContainer == null) {
            return true;
        }
        return listenerContainer.isPauseRequested();
    }

    /**
     * 获取监听器容器的运行状态
     *
     * @param id 容器ID
     * @return true如果容器正在运行，false否则
     * @throws Exception 如果获取容器运行状态失败
     */
    public boolean getRunningStateListenerContainer(String id) throws Exception {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        if (listenerContainer == null) {
            return false;
        }
        return listenerContainer.isRunning();
    }

    /**
     * 设置监听器容器的状态为正常
     *
     * @param id 容器ID
     * @return true如果设置成功，false否则
     * @throws Exception 如果设置容器状态失败
     */
    public boolean setStateNormalListenerContainer(String id) throws Exception {
        if (!existListenerContainer(id)) {
            log.info("Kafka监听容器操作：ID为{}的容器不存在，不操作！", id);
            return false;
        }
        //先判断容器运行状态是否正常，如果不正常，则开启
        if (!getRunningStateListenerContainer(id)) {
            startListenerContainer(id);
        }
        //再判断容器监听状态是否正常，如果不正常，则恢复
        if (getPauseStateListenerContainer(id)) {
            resumeListenerContainer(id);
        }
        //设置完后，再查询状态并返回。
        return isNormalStateListenerContainer(id);
    }


    /**
     * 手动绑定消费者
     *
     * @param topic    主题
     * @param groupId  消费者组ID
     * @param consumer 消费者对象
     * @throws Exception 如果手动绑定消费者失败
     */
    public void manualBindConsumer(String topic, String groupId, IManualBindConsumer consumer) throws Exception {
        boolean b = existTopic(topic);//查询topic是否存在
        log.info("topic-是否存在：" + b);
        if(!b){
            log.info("开始创建kafka主题: "+topic);
            createTopic(topic, 1, (short) 1);//创建topic
        }

        log.info("开始更新topic的过期时间: 1000000");
        updateTopicRetention(topic, String.valueOf(1000000));//更新topic的过期时间

        Set<String> strings = listTopics();//查出所有topic
        log.info("所有topic:" + strings);


        String listenerID = groupId+"_"+topic;

        boolean b1 = existListenerContainer(listenerID);//查询监听容器是否存在
        log.info(listenerID+" 容器-是否存在：" + b1);

        //创建监听容器
        if(!b1) {
            log.info("开始注册监听器容器: "+listenerID);
            registerListenerContainer(listenerID, groupId, consumer, consumer.getClass().getDeclaredMethod("consumerMessage", List.class, Acknowledgment.class), topic);
        }

        boolean normalStateListenerContainer = isNormalStateListenerContainer(listenerID);//查询监听容器是否为正常状态
        log.info(listenerID+" 容器-状态：" + normalStateListenerContainer);
//
//        pauseListenerContainer(listenerID);//暂停监听容器的监听状态
//        boolean pauseStateListenerContainer = getPauseStateListenerContainer(listenerID);//查询监听容器的监听状态
//        System.out.println("容器-监听状态：" + !pauseStateListenerContainer);
//
//
//        stopListenerContainer(listenerID);//暂停监听容器的监听状态
//        boolean runningStateListenerContainer = getRunningStateListenerContainer(listenerID);//查询监听容器的监听状态
//        System.out.println("容器-运行状态：" + runningStateListenerContainer);
//
//
//        boolean normalStateListenerContainer2 = isNormalStateListenerContainer(listenerID);//查询监听容器是否为正常状态
//        System.out.println("容器-状态：" + normalStateListenerContainer2);

        if(!normalStateListenerContainer) {
            log.info("开始设置监听器容器为正常状态");
            boolean b2 = setStateNormalListenerContainer(listenerID);//设置监听容器为正常状态
            boolean normalStateListenerContainer3 = isNormalStateListenerContainer(listenerID);//查询监听容器是否为正常状态
            log.info(listenerID + " 容器-状态：" + normalStateListenerContainer3);
        }
    }

}
