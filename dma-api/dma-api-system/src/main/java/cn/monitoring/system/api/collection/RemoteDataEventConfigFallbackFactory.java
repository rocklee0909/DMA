package cn.monitoring.system.api.collection;

import cn.monitoring.common.core.domain.R;
import cn.monitoring.system.api.RemoteDataEventConfigService;
import cn.monitoring.system.api.domain.RemoteDataEventConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 文件服务降级处理
 * 
 * @author liru
 */
@Component
public class RemoteDataEventConfigFallbackFactory implements FallbackFactory<RemoteDataEventConfigService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDataEventConfigFallbackFactory.class);

    @Override
    public RemoteDataEventConfigService create(Throwable throwable)
    {
        log.error("事件配置服务调用失败:{}", throwable.getMessage());
        return new RemoteDataEventConfigService()
        {
            @Override
            public R<RemoteDataEventConfig> update(RemoteDataEventConfig dataEventConfig) {
                return R.fail("修改事件配置失败:" + throwable.getMessage());
            }
        };
    }
}
