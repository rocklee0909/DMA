package cn.monitoring.collection.protocol;

import cn.monitoring.collection.domain.CollectorInfo;

/**
 * 协议处理器
 * @author liru
 */
public interface IProtocolHandler {
    /**
     * 处理数据库结构
     * @param info 采集器信息
     * @param data 协议数据
     */
    void handleDBStructure(long collectTime,CollectorInfo info, Object data);

    /**
     * 处理协议数据
     * @param info 采集器信息
     * @param data 协议数据
     */
    void handle(long collectTime,CollectorInfo info, Object data);
}