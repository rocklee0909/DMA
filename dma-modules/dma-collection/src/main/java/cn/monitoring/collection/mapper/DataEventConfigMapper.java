package cn.monitoring.collection.mapper;

import java.util.List;
import cn.monitoring.collection.domain.DataEventConfig;

/**
 * 事件配置Mapper接口
 * 
 * @author liru
 * @date 2025-01-03
 */
public interface DataEventConfigMapper 
{
    /**
     * 查询事件配置
     * 
     * @param eventId 事件配置主键
     * @return 事件配置
     */
    public DataEventConfig selectDataEventConfigByEventId(Long eventId);

    /**
     * 查询事件配置列表
     * 
     * @param dataEventConfig 事件配置
     * @return 事件配置集合
     */
    public List<DataEventConfig> selectDataEventConfigList(DataEventConfig dataEventConfig);

    /**
     * 新增事件配置
     * 
     * @param dataEventConfig 事件配置
     * @return 结果
     */
    public int insertDataEventConfig(DataEventConfig dataEventConfig);

    /**
     * 修改事件配置
     * 
     * @param dataEventConfig 事件配置
     * @return 结果
     */
    public int updateDataEventConfig(DataEventConfig dataEventConfig);

    /**
     * 删除事件配置
     * 
     * @param eventId 事件配置主键
     * @return 结果
     */
    public int deleteDataEventConfigByEventId(Long eventId);

    /**
     * 批量删除事件配置
     * 
     * @param eventIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataEventConfigByEventIds(Long[] eventIds);
}
