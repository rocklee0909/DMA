package cn.monitoring.job.task;

import org.springframework.stereotype.Component;
import cn.monitoring.common.core.utils.StringUtils;

/**
 * 定时任务调度测试
 * 
 * @author liru
 */
@Component("dmaTask")
public class DmaTask
{
    public void dmaMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void dmaParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void dmaNoParams()
    {
        System.out.println("执行无参方法");
    }
}
