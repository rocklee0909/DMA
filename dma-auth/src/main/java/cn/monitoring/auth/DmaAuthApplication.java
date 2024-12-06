package cn.monitoring.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import cn.monitoring.common.security.annotation.EnableRyFeignClients;

/**
 * 认证授权中心
 * 
 * @author liru
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DmaAuthApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaAuthApplication.class, args);
        System.out.println("DMA  认证授权中心启动成功");
    }
}
