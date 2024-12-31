package cn.monitoring.factory.service;

import java.util.List;
import cn.monitoring.factory.domain.EquipmentModule;

/**
 * 设备模块信息-子设备Service接口
 * 
 * @author liru
 * @date 2024-12-28
 */
public interface IEquipmentModuleService 
{
    /**
     * 查询设备模块信息-子设备
     * 
     * @param equipmentModuleId 设备模块信息-子设备主键
     * @return 设备模块信息-子设备
     */
    public EquipmentModule selectEquipmentModuleByEquipmentModuleId(Long equipmentModuleId);

    /**
     * 查询设备模块信息-子设备列表
     * 
     * @param equipmentModule 设备模块信息-子设备
     * @return 设备模块信息-子设备集合
     */
    public List<EquipmentModule> selectEquipmentModuleList(EquipmentModule equipmentModule);

    /**
     * 新增设备模块信息-子设备
     * 
     * @param equipmentModule 设备模块信息-子设备
     * @return 结果
     */
    public int insertEquipmentModule(EquipmentModule equipmentModule);

    /**
     * 修改设备模块信息-子设备
     * 
     * @param equipmentModule 设备模块信息-子设备
     * @return 结果
     */
    public int updateEquipmentModule(EquipmentModule equipmentModule);

    /**
     * 批量删除设备模块信息-子设备
     * 
     * @param equipmentModuleIds 需要删除的设备模块信息-子设备主键集合
     * @return 结果
     */
    public int deleteEquipmentModuleByEquipmentModuleIds(Long[] equipmentModuleIds);

    /**
     * 删除设备模块信息-子设备信息
     * 
     * @param equipmentModuleId 设备模块信息-子设备主键
     * @return 结果
     */
    public int deleteEquipmentModuleByEquipmentModuleId(Long equipmentModuleId);
}
