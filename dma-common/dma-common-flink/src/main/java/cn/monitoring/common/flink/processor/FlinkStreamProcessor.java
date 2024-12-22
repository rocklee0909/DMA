package cn.monitoring.common.flink.processor;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class FlinkStreamProcessor {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "47.96.130.15:9092");
        properties.setProperty("group.id", "dmaDataGroup");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("dmaDataReceive", new SimpleStringSchema(), properties);
        env.addSource(consumer)
                .map(value -> processMessage(value)) // 实际的处理逻辑
                .print();

        env.execute("Flink Kafka Streaming");
    }

    private static String processMessage(String value) {
        // 示例处理逻辑
        return "Processed: " + value;
    }
}
