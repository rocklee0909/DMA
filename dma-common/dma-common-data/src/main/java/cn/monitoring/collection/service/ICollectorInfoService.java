package cn.monitoring.collection.service;

import java.util.List;
import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.domain.DataPoint;
import cn.monitoring.collection.domain.vo.DataTreeSelect;

/**
 * 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等Service接口
 * 
 * @author liru
 * @date 2024-12-15
 */
public interface ICollectorInfoService 
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
     * 批量删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     * 
     * @param collectorIds 需要删除的采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等主键集合
     * @return 结果
     */
    public int deleteCollectorInfoByCollectorIds(Long[] collectorIds);

    /**
     * 删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等信息
     * 
     * @param collectorId 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等主键
     * @return 结果
     */
    public int deleteCollectorInfoByCollectorId(Long collectorId);

//    /**
//     * 添加数据处理任务
//     * @param collectorInfo
//     */
//    void addJob(CollectorInfo collectorInfo);

    /**
     * 根据主题查询采集器信息
     * @param topic
     * @return
     */
    List<CollectorInfo> selectCollectorInfoByDmaTopic(String topic);

    /**
     * 根据主题和组查询采集器信息
     * @param topic
     * @param groupId
     * @return
     */
    List<CollectorInfo> selectCollectorInfoByDmaTopicAndDmaGroup(String topic, String groupId);

    /**
     * 查询采集器树结构
     * @param collectorInfo
     * @return
     */
    List<DataTreeSelect> selectCollectorTreeList(CollectorInfo collectorInfo);

    /**
     * 校验采集器ID、主题、数据点位编码是否唯一
     * @param dataPoint
     * @return
     */
    boolean checkCollectorIdAndTopicAndPointCodeUnique(DataPoint dataPoint);
}
