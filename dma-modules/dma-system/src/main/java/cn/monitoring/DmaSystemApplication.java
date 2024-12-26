package cn.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.monitoring.common.security.annotation.EnableCustomConfig;
import cn.monitoring.common.security.annotation.EnableRyFeignClients;

/**
 * 系统模块
 * 
 * @author liru
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class DmaSystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaSystemApplication.class, args);
        System.out.println("DMA  系统模块启动成功");
    }
}
