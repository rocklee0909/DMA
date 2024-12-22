package cn.monitoring.common.flink.configuration;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlinkConfiguration {

    @Autowired
    private FlinkProperties flinkProperties;

    /**
     * 创建StreamExecutionEnvironment实例
     *
     * @return StreamExecutionEnvironment实例
     */
    @Bean
    public StreamExecutionEnvironment streamExecutionEnvironment() {
        // 创建Configuration实例
        org.apache.flink.configuration.Configuration configuration = new org.apache.flink.configuration.Configuration();
        // 设置JobManager地址，这里假设地址是localhost，端口是8081，你需要根据实际情况替换
        configuration.setString("jobmanager.rpc.address", "113.44.157.96");
        configuration.setString("jobmanager.rpc.port", "8081");

        // 获取StreamExecutionEnvironment实例
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);
        // 这里可以根据实际需求进一步配置环境，比如设置并行度等
        // env.setParallelism(3);  // 示例，设置并行度为3，按需调整
        env.enableCheckpointing(60000);
        // 设置Checkpoint的模式，例如EXACTLY_ONCE保证精准一次语义
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        return env;
    }
}
