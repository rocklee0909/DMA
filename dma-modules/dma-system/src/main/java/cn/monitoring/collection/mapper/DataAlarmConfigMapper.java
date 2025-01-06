package cn.monitoring.collection.mapper;

import java.util.List;
import cn.monitoring.collection.domain.DataAlarmConfig;

/**
 * 告警配置Mapper接口
 * 
 * @author liru
 * @date 2025-01-03
 */
public interface DataAlarmConfigMapper 
{
    /**
     * 查询告警配置
     * 
     * @param alertId 告警配置主键
     * @return 告警配置
     */
    public DataAlarmConfig selectDataAlarmConfigByAlertId(Long alertId);

    /**
     * 查询告警配置列表
     * 
     * @param dataAlarmConfig 告警配置
     * @return 告警配置集合
     */
    public List<DataAlarmConfig> selectDataAlarmConfigList(DataAlarmConfig dataAlarmConfig);

    /**
     * 新增告警配置
     * 
     * @param dataAlarmConfig 告警配置
     * @return 结果
     */
    public int insertDataAlarmConfig(DataAlarmConfig dataAlarmConfig);

    /**
     * 修改告警配置
     * 
     * @param dataAlarmConfig 告警配置
     * @return 结果
     */
    public int updateDataAlarmConfig(DataAlarmConfig dataAlarmConfig);

    /**
     * 删除告警配置
     * 
     * @param alertId 告警配置主键
     * @return 结果
     */
    public int deleteDataAlarmConfigByAlertId(Long alertId);

    /**
     * 批量删除告警配置
     * 
     * @param alertIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataAlarmConfigByAlertIds(Long[] alertIds);
}
