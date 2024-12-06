package cn.monitoring.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.monitoring.common.security.annotation.EnableCustomConfig;
import cn.monitoring.common.security.annotation.EnableRyFeignClients;

/**
 * 代码生成
 * 
 * @author liru
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class DmaGenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaGenApplication.class, args);
        System.out.println("DMA  代码生成模块启动成功");
    }
}
