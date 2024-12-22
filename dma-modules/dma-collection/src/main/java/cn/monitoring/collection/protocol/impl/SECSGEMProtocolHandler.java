package cn.monitoring.collection.protocol.impl;

import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.protocol.IProtocolHandler;
import org.springframework.stereotype.Component;

/**
 * SECS-GEM协议处理器
 * @author liru
 * @date 2024-12-15
 */
@Component
public class SECSGEMProtocolHandler implements IProtocolHandler {

    @Override
    public void handleDBStructure(long receiveTime, CollectorInfo info, Object data) {

    }

    @Override
    public void handle(long receiveTime, CollectorInfo info, Object data) {

    }
}
