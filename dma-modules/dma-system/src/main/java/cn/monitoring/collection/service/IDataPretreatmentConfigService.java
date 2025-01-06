package cn.monitoring.collection.service;

import java.util.List;
import cn.monitoring.collection.domain.DataPretreatmentConfig;

/**
 * 预处理规则配置Service接口
 * 
 * @author liru
 * @date 2025-01-03
 */
public interface IDataPretreatmentConfigService 
{
    /**
     * 查询预处理规则配置
     * 
     * @param pretreatmentId 预处理规则配置主键
     * @return 预处理规则配置
     */
    public DataPretreatmentConfig selectDataPretreatmentConfigByPretreatmentId(Long pretreatmentId);

    /**
     * 查询预处理规则配置列表
     * 
     * @param dataPretreatmentConfig 预处理规则配置
     * @return 预处理规则配置集合
     */
    public List<DataPretreatmentConfig> selectDataPretreatmentConfigList(DataPretreatmentConfig dataPretreatmentConfig);

    /**
     * 新增预处理规则配置
     * 
     * @param dataPretreatmentConfig 预处理规则配置
     * @return 结果
     */
    public int insertDataPretreatmentConfig(DataPretreatmentConfig dataPretreatmentConfig);

    /**
     * 修改预处理规则配置
     * 
     * @param dataPretreatmentConfig 预处理规则配置
     * @return 结果
     */
    public int updateDataPretreatmentConfig(DataPretreatmentConfig dataPretreatmentConfig);

    /**
     * 批量删除预处理规则配置
     * 
     * @param pretreatmentIds 需要删除的预处理规则配置主键集合
     * @return 结果
     */
    public int deleteDataPretreatmentConfigByPretreatmentIds(Long[] pretreatmentIds);

    /**
     * 删除预处理规则配置信息
     * 
     * @param pretreatmentId 预处理规则配置主键
     * @return 结果
     */
    public int deleteDataPretreatmentConfigByPretreatmentId(Long pretreatmentId);
}
