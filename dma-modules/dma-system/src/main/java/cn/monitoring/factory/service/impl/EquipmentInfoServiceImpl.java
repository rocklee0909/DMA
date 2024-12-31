package cn.monitoring.factory.service.impl;

import java.util.List;

import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import cn.monitoring.factory.domain.EquipmentModule;
import cn.monitoring.factory.domain.vo.EquipmentVo;
import cn.monitoring.factory.mapper.EquipmentModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.factory.mapper.EquipmentInfoMapper;
import cn.monitoring.factory.domain.EquipmentInfo;
import cn.monitoring.factory.service.IEquipmentInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 设备基本信息Service业务层处理
 * 
 * @author liru
 * @date 2024-12-27
 */
@Service
public class EquipmentInfoServiceImpl implements IEquipmentInfoService 
{
    @Autowired
    private EquipmentInfoMapper equipmentInfoMapper;

    @Autowired
    private EquipmentModuleMapper equipmentModuleMapper;

    /**
     * 查询设备基本信息
     *
     * @param equipmentInfoId 设备基本信息主键
     * @return 设备基本信息
     */
    @Override
    public EquipmentVo selectEquipmentInfoByEquipmentInfoId(Long equipmentInfoId)
    {
        EquipmentInfo equipmentInfo = equipmentInfoMapper.selectEquipmentInfoByEquipmentInfoId(equipmentInfoId);
        List<EquipmentModule> equipmentModules = equipmentModuleMapper.selectEquipmentModuleList(new EquipmentModule(equipmentInfo.getEquipmentInfoId()));
        return new EquipmentVo(equipmentInfo,equipmentModules);
    }

    /**
     * 查询设备基本信息列表
     * 
     * @param equipmentInfo 设备基本信息
     * @return 设备基本信息
     */
    @Override
    public List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo)
    {
        return equipmentInfoMapper.selectEquipmentInfoList(equipmentInfo);
    }

    /**
     * 新增设备基本信息
     * 
     * @param equipmentInfo 设备基本信息
     * @return 结果
     */
    @Override
    public int insertEquipmentInfo(EquipmentInfo equipmentInfo)
    {
        equipmentInfo.setCreateBy(SecurityUtils.getUsername());
        equipmentInfo.setCreateTime(DateUtils.getNowDate());
        return equipmentInfoMapper.insertEquipmentInfo(equipmentInfo);
    }

    /**
     * 新增设备基本信息
     * @param equipmentVo 设备基本信息,子设备信息
     * @return
     */
    @Transactional
    @Override
    public int insertEquipmentInfo(EquipmentVo equipmentVo) {
        EquipmentInfo equipmentInfo = equipmentVo.getEquipmentInfo();
        List<EquipmentModule> equipmentModules = equipmentVo.getModules();

        if(equipmentInfo.getEquipmentInfoId()==null){
            equipmentInfo.setCreateBy(SecurityUtils.getUsername());
            equipmentInfo.setCreateTime(DateUtils.getNowDate());
            equipmentInfoMapper.insertEquipmentInfo(equipmentInfo);
        }else{
            equipmentInfo.setUpdateBy(SecurityUtils.getUsername());
            equipmentInfo.setUpdateTime(DateUtils.getNowDate());
            equipmentInfoMapper.updateEquipmentInfo(equipmentInfo);
        }

        for (EquipmentModule equipmentModule : equipmentModules) {

            if(equipmentModule.getEquipmentModuleId()==null){
                equipmentModule.setCreateBy(SecurityUtils.getUsername());
                equipmentModule.setCreateTime(DateUtils.getNowDate());
                equipmentModule.setEquipmentInfoId(equipmentInfo.getEquipmentInfoId());
                equipmentModule.setEquipmentInfoName(equipmentInfo.getEquipmentCode());
                equipmentModuleMapper.insertEquipmentModule(equipmentModule);
            }else{
                equipmentModule.setUpdateBy(SecurityUtils.getUsername());
                equipmentModule.setUpdateTime(DateUtils.getNowDate());
                equipmentModule.setEquipmentInfoId(equipmentInfo.getEquipmentInfoId());
                equipmentModule.setEquipmentInfoName(equipmentInfo.getEquipmentCode());
                equipmentModuleMapper.updateEquipmentModule(equipmentModule);
            }
        }
        return 1;
    }

    /**
     * 修改设备基本信息
     * 
     * @param equipmentInfo 设备基本信息
     * @return 结果
     */
    @Override
    public int updateEquipmentInfo(EquipmentInfo equipmentInfo)
    {
        equipmentInfo.setUpdateBy(SecurityUtils.getUsername());
        equipmentInfo.setUpdateTime(DateUtils.getNowDate());
        return equipmentInfoMapper.updateEquipmentInfo(equipmentInfo);
    }

    @Transactional
    @Override
    public int updateEquipmentInfo(EquipmentVo equipmentVo) {
        return insertEquipmentInfo(equipmentVo);
    }

    /**
     * 批量删除设备基本信息
     * 
     * @param equipmentInfoIds 需要删除的设备基本信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipmentInfoByEquipmentInfoIds(Long[] equipmentInfoIds)
    {
        for (Long equipmentInfoId : equipmentInfoIds){
            List<EquipmentModule> equipmentModules = equipmentModuleMapper.selectEquipmentModuleList(new EquipmentModule(equipmentInfoId));
            if(equipmentModules.size()>0){
                equipmentInfoMapper.deleteEquipmentInfoByEquipmentInfoIds(equipmentModules.stream()
                        .map(EquipmentModule::getEquipmentModuleId)
                        .toArray(Long[]::new));
            }
        }

        return equipmentInfoMapper.deleteEquipmentInfoByEquipmentInfoIds(equipmentInfoIds);
    }

    /**
     * 删除设备基本信息信息
     * 
     * @param equipmentInfoId 设备基本信息主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentInfoByEquipmentInfoId(Long equipmentInfoId)
    {
        return equipmentInfoMapper.deleteEquipmentInfoByEquipmentInfoId(equipmentInfoId);
    }
}
