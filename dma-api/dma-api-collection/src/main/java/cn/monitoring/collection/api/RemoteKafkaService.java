package cn.monitoring.collection.api;

import cn.monitoring.collection.api.datapoint.RemoteKafkaFallbackFactory;
import cn.monitoring.common.core.constant.ServiceNameConstants;
import cn.monitoring.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 采集数据点服务
 * 
 * @author liru
 */
@FeignClient(contextId = "remoteDataPointService", value = ServiceNameConstants.COLLECTION_SERVICE, fallbackFactory = RemoteKafkaFallbackFactory.class)
public interface RemoteKafkaService
{
    @PostMapping("/collection/manualBind")
    public R<Boolean> manualBind(@RequestParam("dmaTopic") String dmaTopic,@RequestParam("dmaGroup") String dmaGroup);

    @PostMapping("/deleteTopic")
    public R<Boolean> deleteTopic(@RequestParam("dmaTopic") String dmaTopic);
}
