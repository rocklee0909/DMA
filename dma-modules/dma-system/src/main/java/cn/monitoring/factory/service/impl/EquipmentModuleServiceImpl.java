package cn.monitoring.factory.service.impl;

import java.util.List;

import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.factory.mapper.EquipmentModuleMapper;
import cn.monitoring.factory.domain.EquipmentModule;
import cn.monitoring.factory.service.IEquipmentModuleService;

/**
 * 设备模块信息-子设备Service业务层处理
 * 
 * @author liru
 * @date 2024-12-28
 */
@Service
public class EquipmentModuleServiceImpl implements IEquipmentModuleService 
{
    @Autowired
    private EquipmentModuleMapper equipmentModuleMapper;

    /**
     * 查询设备模块信息-子设备
     * 
     * @param equipmentModuleId 设备模块信息-子设备主键
     * @return 设备模块信息-子设备
     */
    @Override
    public EquipmentModule selectEquipmentModuleByEquipmentModuleId(Long equipmentModuleId)
    {
        return equipmentModuleMapper.selectEquipmentModuleByEquipmentModuleId(equipmentModuleId);
    }

    /**
     * 查询设备模块信息-子设备列表
     * 
     * @param equipmentModule 设备模块信息-子设备
     * @return 设备模块信息-子设备
     */
    @Override
    public List<EquipmentModule> selectEquipmentModuleList(EquipmentModule equipmentModule)
    {
        return equipmentModuleMapper.selectEquipmentModuleList(equipmentModule);
    }

    /**
     * 新增设备模块信息-子设备
     * 
     * @param equipmentModule 设备模块信息-子设备
     * @return 结果
     */
    @Override
    public int insertEquipmentModule(EquipmentModule equipmentModule)
    {
        equipmentModule.setCreateBy(SecurityUtils.getUsername());
        equipmentModule.setCreateTime(DateUtils.getNowDate());
        return equipmentModuleMapper.insertEquipmentModule(equipmentModule);
    }

    /**
     * 修改设备模块信息-子设备
     * 
     * @param equipmentModule 设备模块信息-子设备
     * @return 结果
     */
    @Override
    public int updateEquipmentModule(EquipmentModule equipmentModule)
    {
        equipmentModule.setUpdateBy(SecurityUtils.getUsername());
        equipmentModule.setUpdateTime(DateUtils.getNowDate());
        return equipmentModuleMapper.updateEquipmentModule(equipmentModule);
    }

    /**
     * 批量删除设备模块信息-子设备
     * 
     * @param equipmentModuleIds 需要删除的设备模块信息-子设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentModuleByEquipmentModuleIds(Long[] equipmentModuleIds)
    {
        return equipmentModuleMapper.deleteEquipmentModuleByEquipmentModuleIds(equipmentModuleIds);
    }

    /**
     * 删除设备模块信息-子设备信息
     * 
     * @param equipmentModuleId 设备模块信息-子设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentModuleByEquipmentModuleId(Long equipmentModuleId)
    {
        return equipmentModuleMapper.deleteEquipmentModuleByEquipmentModuleId(equipmentModuleId);
    }
}
