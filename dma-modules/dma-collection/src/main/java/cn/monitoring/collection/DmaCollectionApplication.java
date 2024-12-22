package cn.monitoring.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.monitoring.common.security.annotation.EnableCustomConfig;
import cn.monitoring.common.security.annotation.EnableRyFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 数据接收
 * 
 * @author liru
 */
@EnableAsync
@EnableKafka
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan("cn.monitoring")
public class DmaCollectionApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaCollectionApplication.class, args);
        System.out.println("DMA  数据接收启动成功");
    }
}
