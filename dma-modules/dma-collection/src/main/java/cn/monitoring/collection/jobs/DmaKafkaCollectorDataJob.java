package cn.monitoring.collection.jobs;

import cn.monitoring.common.core.utils.StringUtils;
import cn.monitoring.common.flink.job.FlinkJob;
import lombok.Getter;
import lombok.Setter;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * DMA Kafka 采集数据作业任务
 * @author liru
 */
@Component
public class DmaKafkaCollectorDataJob implements FlinkJob {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Setter
    private String jobName;

    @Override
    public String getJobName() {
        return jobName;
    }

    @Setter
    @Getter
    private String groupId;

    @Setter
    @Getter
    private String topic;

    @Override
    public void execute(StreamExecutionEnvironment env) {
        for (String bootstrapServer : kafkaProperties.getBootstrapServers()) {

            // 判断是否设置groupId和topic
            if(StringUtils.isBlank(groupId) || StringUtils.isBlank(topic)){
                continue;
            }

            KafkaSource<String> source = KafkaSource.<String>builder()
                    .setBootstrapServers(bootstrapServer)
//                  // 消费所有主题 Pattern方式
//                  .setTopicPattern("topic.*")
//                  // 消费指定主题
//                  .setTopics("topic-a", "topic-b")
                    .setGroupId(groupId)
                    .setTopics(topic)

//                    .setTopics(groupId)
//                    .setGroupId(topic)
//                    // 从消费组提交的位点开始消费，不指定位点重置策略
//                    .setStartingOffsets(OffsetsInitializer.committedOffsets())
//                    // 从消费组提交的位点开始消费，如果提交位点不存在，使用最早位点
//                    .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
//                    // 从时间戳大于等于指定时间戳（毫秒）的数据开始消费
//                    .setStartingOffsets(OffsetsInitializer.timestamp(1657256176000L))
//                    // 从最早位点开始消费
//                    .setStartingOffsets(OffsetsInitializer.earliest())
                    // 从最末尾位点开始消费
                    .setStartingOffsets(OffsetsInitializer.latest())


                    // 配置自动提交偏移量，设置提交模式为ON_CHECKPOINTS


                    .setValueOnlyDeserializer(new SimpleStringSchema())
                    .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(StringDeserializer.class))
                    //.setProperty("partition.discovery.interval.ms", "60000")
                    .build();


//            // 消费指定分区方式
//            final HashSet<TopicPartition> partitionSet = new HashSet<>(Arrays.asList(
//                    new TopicPartition("topic-a", 0),    // Partition 0 of topic "topic-a"
//                    new TopicPartition("topic-b", 5)));  // Partition 5 of topic "topic-b"
//            KafkaSource.builder().setPartitions(partitionSet);



            DataStream<String> stream =  env.fromSource(source, WatermarkStrategy.forMonotonousTimestamps(), "GroupId:"+groupId+",topic:"+topic+" Dma Collector Kafka Source");
            // 数据清洗，去除空消息等无效数据
            DataStream<String> cleanedStream = stream.filter(message -> message!= null &&!message.isEmpty());




            //stream.print();

            // 或者进行其他转换操作
            DataStream<String> transformedStream = stream.map(new MyMapFunction());

            transformedStream.print();


        }
    }

    private static class MyMapFunction implements MapFunction<String, String> {
        @Override
        public String map(String value) throws Exception {
            // 在这里实现转换逻辑
            return value.toUpperCase(); // 例如，将字符串转换为大写
        }
    }

}
