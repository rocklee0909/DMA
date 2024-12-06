package cn.monitoring.modules.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 监控中心
 * 
 * @author liru
 */
@EnableAdminServer
@SpringBootApplication
public class DmaMonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaMonitorApplication.class, args);
        System.out.println("DMA  监控中心启动成功");
    }
}
