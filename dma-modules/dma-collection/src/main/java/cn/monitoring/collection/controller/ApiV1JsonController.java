package cn.monitoring.collection.controller;

import cn.hutool.json.JSON;
import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.protocol.IProtocolHandler;
import cn.monitoring.collection.service.ICollectorInfoService;
import cn.monitoring.common.core.utils.StringUtils;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.log.annotation.Log;
import cn.monitoring.common.log.enums.BusinessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/api/v1/json")
public class ApiV1JsonController extends BaseController {

    // 用于存储模块名称与协议处理器的映射关系，这里使用ConcurrentHashMap保证线程安全
    private final Map<String, IProtocolHandler> protocolHandlerMap = new ConcurrentHashMap<>();

    @Autowired
    private ICollectorInfoService collectorInfoService;

    // 通过构造函数注入，将实现了IProtocolHandler接口的所有Bean注入到map中
    @Autowired
    public ApiV1JsonController(List<IProtocolHandler> protocolHandlers) {
        for (IProtocolHandler handler : protocolHandlers) {
            protocolHandlerMap.put(handler.getClass().getSimpleName().replace("ProtocolHandler", "").toLowerCase(), handler);
        }
    }

    @Log(title = "远程写入采集器数据", businessType = BusinessType.INSERT)
    @PostMapping("/write")
    public AjaxResult write(@RequestParam("topic") String topic, @RequestBody JSON msg)
    {
        Long receiveTime = new Date().getTime();

        log.info("数据接收时间{},主题:{},消息内容:{}",receiveTime,topic,msg);

        //空数据过滤处理
        if (msg==null || StringUtils.isBlank(msg.toString())) {
            return error("空数据，拒绝处理!");
        }

        //数据整理
        //根据topic查询采集器信息 根据“采集器名称”创建clickhouse数据存储表
        List<CollectorInfo> collectorInfo = collectorInfoService.selectCollectorInfoByDmaTopic(topic);
        for (CollectorInfo info : collectorInfo) {
            log.info("采集器信息:{}",info);
            //采集器模块名称
            String moduleName = info.getModuleName();

            // 根据模块名称从map中获取对应的协议处理器，如果不存在则记录错误日志并继续处理下一个
            IProtocolHandler handler = protocolHandlerMap.get(moduleName.toLowerCase());
            if (handler!= null) {
                handler.handleDBStructure(receiveTime,info,msg);
                handler.handle(receiveTime,info,msg);
            } else {
                log.error("没有找到对应模块名称 {} 的协议处理器", moduleName);
            }
        }

        return toAjax(true);
    }
}
