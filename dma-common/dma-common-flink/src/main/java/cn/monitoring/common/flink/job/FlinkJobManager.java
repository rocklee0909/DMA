package cn.monitoring.common.flink.job;

import cn.monitoring.common.flink.configuration.FlinkProperties;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * flink任务管理器
 * @author liru
 */
@Component
public class FlinkJobManager {

    @Autowired
    private FlinkProperties flinkConfiguration;

    @Autowired
    private StreamExecutionEnvironment env;
    /**
     * 执行任务
     * @param job
     * @return
     * @throws Exception
     */
    public JobExecutionResult executeJob(FlinkJob job) throws Exception {
        //设置并行度
        //env.setParallelism(flinkConfiguration.getParallelism());

        //设置checkpoint 可在此设置点检点相关配置
        //job.setCheckpoint(env);

        //执行任务
        job.execute(env);


        return env.execute(job.getJobName());
    }
}
