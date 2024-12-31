package cn.monitoring.factory.service;

import java.util.List;
import cn.monitoring.factory.domain.EquipmentInfo;
import cn.monitoring.factory.domain.vo.EquipmentVo;

/**
 * 设备基本信息Service接口
 * 
 * @author liru
 * @date 2024-12-27
 */
public interface IEquipmentInfoService 
{
    /**
     * 查询设备基本信息
     *
     * @param equipmentInfoId 设备基本信息主键
     * @return 设备基本信息
     */
    public EquipmentVo selectEquipmentInfoByEquipmentInfoId(Long equipmentInfoId);

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
     * 新增设备基本信息
     *
     * @param equipmentVo 设备基本信息,子设备信息
     * @return 结果
     */
    public int insertEquipmentInfo(EquipmentVo equipmentVo);

    /**
     * 修改设备基本信息
     * 
     * @param equipmentInfo 设备基本信息
     * @return 结果
     */
    public int updateEquipmentInfo(EquipmentInfo equipmentInfo);

    /**
     * 修改设备基本信息
     *
     * @param equipmentVo 设备基本信息,子设备信息
     * @return 结果
     */
    public int updateEquipmentInfo(EquipmentVo equipmentVo);

    /**
     * 批量删除设备基本信息
     * 
     * @param equipmentInfoIds 需要删除的设备基本信息主键集合
     * @return 结果
     */
    public int deleteEquipmentInfoByEquipmentInfoIds(Long[] equipmentInfoIds);

    /**
     * 删除设备基本信息信息
     * 
     * @param equipmentInfoId 设备基本信息主键
     * @return 结果
     */
    public int deleteEquipmentInfoByEquipmentInfoId(Long equipmentInfoId);


}
