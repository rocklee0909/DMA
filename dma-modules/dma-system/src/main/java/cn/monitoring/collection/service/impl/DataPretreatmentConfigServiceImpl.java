package cn.monitoring.collection.service.impl;

import java.util.List;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.collection.mapper.DataPretreatmentConfigMapper;
import cn.monitoring.collection.domain.DataPretreatmentConfig;
import cn.monitoring.collection.service.IDataPretreatmentConfigService;

/**
 * 预处理规则配置Service业务层处理
 * 
 * @author liru
 * @date 2025-01-03
 */
@Service
public class DataPretreatmentConfigServiceImpl implements IDataPretreatmentConfigService 
{
    @Autowired
    private DataPretreatmentConfigMapper dataPretreatmentConfigMapper;

    /**
     * 查询预处理规则配置
     * 
     * @param pretreatmentId 预处理规则配置主键
     * @return 预处理规则配置
     */
    @Override
    public DataPretreatmentConfig selectDataPretreatmentConfigByPretreatmentId(Long pretreatmentId)
    {
        return dataPretreatmentConfigMapper.selectDataPretreatmentConfigByPretreatmentId(pretreatmentId);
    }

    /**
     * 查询预处理规则配置列表
     * 
     * @param dataPretreatmentConfig 预处理规则配置
     * @return 预处理规则配置
     */
    @Override
    public List<DataPretreatmentConfig> selectDataPretreatmentConfigList(DataPretreatmentConfig dataPretreatmentConfig)
    {
        return dataPretreatmentConfigMapper.selectDataPretreatmentConfigList(dataPretreatmentConfig);
    }

    /**
     * 新增预处理规则配置
     * 
     * @param dataPretreatmentConfig 预处理规则配置
     * @return 结果
     */
    @Override
    public int insertDataPretreatmentConfig(DataPretreatmentConfig dataPretreatmentConfig)
    {
        dataPretreatmentConfig.setCreateBy(SecurityUtils.getUsername());
        dataPretreatmentConfig.setCreateTime(DateUtils.getNowDate());
        return dataPretreatmentConfigMapper.insertDataPretreatmentConfig(dataPretreatmentConfig);
    }

    /**
     * 修改预处理规则配置
     * 
     * @param dataPretreatmentConfig 预处理规则配置
     * @return 结果
     */
    @Override
    public int updateDataPretreatmentConfig(DataPretreatmentConfig dataPretreatmentConfig)
    {
        dataPretreatmentConfig.setUpdateBy(SecurityUtils.getUsername());
        dataPretreatmentConfig.setUpdateTime(DateUtils.getNowDate());
        return dataPretreatmentConfigMapper.updateDataPretreatmentConfig(dataPretreatmentConfig);
    }

    /**
     * 批量删除预处理规则配置
     * 
     * @param pretreatmentIds 需要删除的预处理规则配置主键
     * @return 结果
     */
    @Override
    public int deleteDataPretreatmentConfigByPretreatmentIds(Long[] pretreatmentIds)
    {
        return dataPretreatmentConfigMapper.deleteDataPretreatmentConfigByPretreatmentIds(pretreatmentIds);
    }

    /**
     * 删除预处理规则配置信息
     * 
     * @param pretreatmentId 预处理规则配置主键
     * @return 结果
     */
    @Override
    public int deleteDataPretreatmentConfigByPretreatmentId(Long pretreatmentId)
    {
        return dataPretreatmentConfigMapper.deleteDataPretreatmentConfigByPretreatmentId(pretreatmentId);
    }
}
