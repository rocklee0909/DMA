package cn.monitoring.common.flink.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * Flink 配置
 * <pre>
 * spring:
 *   flink:
 *     jobManager:
 *       memory:
 *         heap: "1024m"
 *       numberOfTaskSlots: 4
 *     taskManager:
 *       memory:
 *         heap: "2048m"
 *       numberOfTaskSlots: 8
 *     parallelism:
 *       default: 1
 * </pre>
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.flink")
public class FlinkProperties {

    /**
     * flink服务地址
     */
    private String jobManagerAddress;
    /**
     * 链接配置
     */
    private RestConfig rest;

    /**
     * 配置 JobManager 的内存和任务槽数量
     */
    private JobManager jobManager;
    /**
     * 配置 TaskManager 的内存和任务槽数量
     */
    private TaskManager taskManager;
    /**
     * 配置作业的并行度
     */
    private int parallelism;


    /**
     * 内部类，用于映射rest相关的配置属性
     */
    @Data
    public static class RestConfig {
        private String address;
        private int port;
        private String username;
        private String password;
    }

    @Data
    public static class JobManager{
        /**
         * 配置 JobManager 的内存
         */
        private Memory memory;
        /**
         * 配置 JobManager 的任务槽数量
         */
        private int numberOfTaskSlots;

        @Data
        public static class Memory{
            /**
             * 配置 JobManager 内存的堆大小
             */
            private String heap;
        }
    }

    @Data
    public static class TaskManager{
        /**
         * 配置 TaskManager 的内存
         */
        private Memory memory;
        /**
         * 配置 TaskManager 的任务槽数量
         */
        private int numberOfTaskSlots;

        @Data
        public static class Memory{
            /**
             * 配置 TaskManager 内存的堆大小
             */
            private String heap;
        }
    }
}
