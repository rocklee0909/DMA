package cn.monitoring.collection.controller;

import cn.hutool.json.JSON;
import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.jobs.CollectorInfoDataReceiveJob;
import cn.monitoring.collection.protocol.IProtocolHandler;
import cn.monitoring.collection.service.ICollectorInfoService;
import cn.monitoring.common.core.domain.R;
import cn.monitoring.common.core.utils.SpringUtils;
import cn.monitoring.common.core.utils.StringUtils;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.kafka.consumer.IManualBindConsumer;
import cn.monitoring.common.kafka.utils.KafkaUtil;
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
@RequestMapping("/kafka")
public class KafkaController extends BaseController {


    @Autowired
    private KafkaUtil kafkaUtil;

    @Log(title = "绑定kafka监听", businessType = BusinessType.INSERT)
    @PostMapping("/manualBind")
    public R<Boolean> manualBind(String dmaTopic, String dmaGroup)
    {
        try {
            IManualBindConsumer consumer = SpringUtils.getBean(CollectorInfoDataReceiveJob.class);
            kafkaUtil.manualBindConsumer(dmaTopic, dmaGroup, consumer);
        }catch (Exception e){
            return R.fail(e.getMessage());
        }
        return R.ok();
    }

    @Log(title = "删除kafka监听", businessType = BusinessType.DELETE)
    @PostMapping("/deleteTopic")
    public R<Boolean> deleteTopic(String dmaTopic)
    {
        try {
            kafkaUtil.deleteTopic(dmaTopic);
        } catch (Exception e) {
            log.error("删除主题失败");
            return R.fail(e.getMessage());
        }
        return R.ok();
    }
}
