//package cn.monitoring.collection.runner;
//
//import cn.monitoring.collection.domain.CollectorInfo;
//import cn.monitoring.collection.service.ICollectorInfoService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Slf4j
//@Component
//public class DmaFlinkCommandLineRunner implements CommandLineRunner {
//
//    @Autowired
//    private ICollectorInfoService collectorInfoService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<CollectorInfo> collectorInfos = collectorInfoService.selectCollectorInfoList(new CollectorInfo());
//        for (CollectorInfo collectorInfo : collectorInfos) {
//            // 异步执行Flink作业
//            collectorInfoService.addJob(collectorInfo);
//        }
//    }
//}
