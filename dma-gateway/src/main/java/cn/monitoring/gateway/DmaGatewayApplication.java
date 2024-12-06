package cn.monitoring.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 * 
 * @author liru
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DmaGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaGatewayApplication.class, args);
        System.out.println("DMA  若依网关启动成功");
    }
}
