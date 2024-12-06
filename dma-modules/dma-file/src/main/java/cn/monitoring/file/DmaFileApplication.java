package cn.monitoring.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 文件服务
 * 
 * @author liru
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DmaFileApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DmaFileApplication.class, args);
        System.out.println("DMA  文件服务模块启动成功");
    }
}
