package cn.monitoring.factory.mapper;

import java.util.List;
import cn.monitoring.factory.domain.EquipmentModule;

/**
 * 设备模块信息-子设备Mapper接口
 * 
 * @author liru
 * @date 2024-12-28
 */
public interface EquipmentModuleMapper 
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
     * 删除设备模块信息-子设备
     * 
     * @param equipmentModuleId 设备模块信息-子设备主键
     * @return 结果
     */
    public int deleteEquipmentModuleByEquipmentModuleId(Long equipmentModuleId);

    /**
     * 批量删除设备模块信息-子设备
     * 
     * @param equipmentModuleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentModuleByEquipmentModuleIds(Long[] equipmentModuleIds);
}
