package cn.monitoring.factory.mapper;

import java.util.List;
import cn.monitoring.factory.domain.EquipmentInfo;

/**
 * 设备基本信息Mapper接口
 * 
 * @author liru
 * @date 2024-12-27
 */
public interface EquipmentInfoMapper 
{
    /**
     * 查询设备基本信息
     * 
     * @param equipmentInfoId 设备基本信息主键
     * @return 设备基本信息
     */
    public EquipmentInfo selectEquipmentInfoByEquipmentInfoId(Long equipmentInfoId);

    /**
     * 查询设备基本信息列表
     * 
     * @param equipmentInfo 设备基本信息
     * @return 设备基本信息集合
     */
    public List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo);

    /**
     * 新增设备基本信息
     * 
     * @param equipmentInfo 设备基本信息
     * @return 结果
     */
    public int insertEquipmentInfo(EquipmentInfo equipmentInfo);

    /**
     * 修改设备基本信息
     * 
     * @param equipmentInfo 设备基本信息
     * @return 结果
     */
    public int updateEquipmentInfo(EquipmentInfo equipmentInfo);

    /**
     * 删除设备基本信息
     * 
     * @param equipmentInfoId 设备基本信息主键
     * @return 结果
     */
    public int deleteEquipmentInfoByEquipmentInfoId(Long equipmentInfoId);

    /**
     * 批量删除设备基本信息
     * 
     * @param equipmentInfoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentInfoByEquipmentInfoIds(Long[] equipmentInfoIds);
}
