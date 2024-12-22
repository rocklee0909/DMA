package cn.monitoring.common.flink.job;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Flink 作业接口
 *
 * @author liru
 */
public interface FlinkJob {

    /**
     * 任务名称
     *
     * @return 任务名称
     */
    String getJobName();

    /**
     * 执行任务 @Async
     *
     * @param env 执行环境
     */
    void execute(StreamExecutionEnvironment env);
}
