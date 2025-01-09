package cn.monitoring.system.api;

import cn.monitoring.common.core.constant.ServiceNameConstants;
import cn.monitoring.common.core.domain.R;
import cn.monitoring.system.api.domain.RemoteDataEventConfig;
import cn.monitoring.system.api.factory.RemoteFileFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 事件配置服务
 *
 * @author liru
 */
@FeignClient(contextId = "remoteDataEventConfigService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteDataEventConfigService
{
    /**
     * 修改事件配置
     */
    @PutMapping("/update")
    public R<RemoteDataEventConfig> update(@RequestBody RemoteDataEventConfig dataEventConfig);
}
