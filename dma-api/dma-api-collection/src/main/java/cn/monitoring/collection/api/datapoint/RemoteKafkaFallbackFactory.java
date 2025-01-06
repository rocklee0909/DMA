package cn.monitoring.collection.api.datapoint;

import cn.monitoring.collection.api.RemoteKafkaService;
import cn.monitoring.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 采集点位降级处理
 * 
 * @author liru
 */
@Component
public class RemoteKafkaFallbackFactory implements FallbackFactory<RemoteKafkaService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteKafkaFallbackFactory.class);

    @Override
    public RemoteKafkaService create(Throwable throwable)
    {
        log.error("采集服务调用失败:{}", throwable.getMessage());
        return new RemoteKafkaService()
        {

            @Override
            public R<Boolean> manualBind(String dmaTopic, String dmaGroup) {
                return R.fail("注册kafka主题监听失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> deleteTopic(String dmaTopic) {
                return R.fail("删除kafka已注册主题失败:" + throwable.getMessage());
            }
        };
    }
}
