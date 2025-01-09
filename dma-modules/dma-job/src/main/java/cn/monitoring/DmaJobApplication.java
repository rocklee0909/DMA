package cn.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.monitoring.common.security.annotation.EnableCustomConfig;
import cn.monitoring.common.security.annotation.EnableRyFeignClients;

/**
 * 定时任务
 * 
 * @author liru
 */
@EnableCustomConfig
@EnableRyFeignClients   
@SpringBootApplication
public class DmaJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaJobApplication.class, args);
        System.out.println("DMA  定时任务模块启动成功");
    }
}
