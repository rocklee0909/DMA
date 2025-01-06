package cn.monitoring.collection.mapper;

import java.util.List;
import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.domain.DataPoint;

/**
 * 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等Mapper接口
 * 
 * @author liru
 * @date 2024-12-15
 */
public interface CollectorInfoMapper 
{
    /**
     * 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorId 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等主键
     * @return 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     */
    public CollectorInfo selectCollectorInfoByCollectorId(Long collectorId);

    /**
     * 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等列表
     * 
     * @param collectorInfo 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * @return 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等集合
     */
    public List<CollectorInfo> selectCollectorInfoList(CollectorInfo collectorInfo);

    /**
     * 新增采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorInfo 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * @return 结果
     */
    public int insertCollectorInfo(CollectorInfo collectorInfo);

    /**
     * 修改采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorInfo 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * @return 结果
     */
    public int updateCollectorInfo(CollectorInfo collectorInfo);

    /**
     * 删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorId 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等主键
     * @return 结果
     */
    public int deleteCollectorInfoByCollectorId(Long collectorId);

    /**
     * 批量删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCollectorInfoByCollectorIds(Long[] collectorIds);

    /**
     * 根据主题查询采集器信息
     * @param dmaTopic 采集器主题
     * @return
     */
    public List<CollectorInfo> selectCollectorInfoByDmaTopic(String dmaTopic);

    /**
     * 根据主题和组查询采集器信息
     * @param topic
     * @param groupId
     * @return
     */
    List<CollectorInfo> selectCollectorInfoByDmaTopicAndDmaGroup(String topic, String groupId);

    /**
     * 校验采集器ID、主题、数据点位编码是否唯一
     * @param dataPoint
     * @return
     */
    int checkCollectorIdAndTopicAndPointCodeUnique(DataPoint dataPoint);
}
