package cn.monitoring.collection.runner;

import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.jobs.CollectorInfoDataReceiveJob;
import cn.monitoring.collection.service.ICollectorInfoService;
import cn.monitoring.common.core.utils.SpringUtils;
import cn.monitoring.common.kafka.consumer.IManualBindConsumer;
import cn.monitoring.common.kafka.utils.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class DmaStartUpCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ICollectorInfoService collectorInfoService;



    @Autowired
    private KafkaUtil kafkaUtil;

    @Async
    @Override
    public void run(String... args) throws Exception {
        List<CollectorInfo> collectorInfos = collectorInfoService.selectCollectorInfoList(new CollectorInfo());
        for (CollectorInfo collectorInfo : collectorInfos) {
            try {
                IManualBindConsumer consumer = SpringUtils.getBean(CollectorInfoDataReceiveJob.class);
                kafkaUtil.manualBindConsumer(collectorInfo.getDmaTopic(), collectorInfo.getDmaGroup(), consumer);
            }catch (Exception e){
                log.info(e.getMessage(),e);
            }
        }
    }
}
