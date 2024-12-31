package cn.monitoring.factory.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 设备模块信息-子设备对象 equipment_module
 * 
 * @author liru
 * @date 2024-12-28
 */
public class EquipmentModule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 子设备ID */
    private Long equipmentModuleId;

    /** 设备信息ID */
    @Excel(name = "设备信息ID")
    private Long equipmentInfoId;

    /** 所属设备编号 */
    @Excel(name = "所属设备编号")
    private String equipmentInfoName;

    /** 子设备编号 */
    @Excel(name = "子设备编号")
    private String equipmentCode;

    /** 子设备名称 */
    @Excel(name = "子设备名称")
    private String equipmentName;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String equipmentModel;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String equipmentType;

    /** 设备供应商 */
    @Excel(name = "设备供应商")
    private String supplier;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String equipmentStatus;

    /** 设备主要功能 */
    @Excel(name = "设备主要功能")
    private String mainFunction;

    /** 设备技术规格 */
    @Excel(name = "设备技术规格")
    private String technicalSpecifications;

    public EquipmentModule() {
    }

    public EquipmentModule(Long equipmentInfoId) {
        this.equipmentInfoId = equipmentInfoId;
    }

    public void setEquipmentModuleId(Long equipmentModuleId) 
    {
        this.equipmentModuleId = equipmentModuleId;
    }

    public Long getEquipmentModuleId() 
    {
        return equipmentModuleId;
    }
    public void setEquipmentInfoId(Long equipmentInfoId) 
    {
        this.equipmentInfoId = equipmentInfoId;
    }

    public Long getEquipmentInfoId() 
    {
        return equipmentInfoId;
    }
    public void setEquipmentInfoName(String equipmentInfoName)
    {
        this.equipmentInfoName = equipmentInfoName;
    }

    public String getEquipmentInfoName()
    {
        return equipmentInfoName;
    }
    public void setEquipmentCode(String equipmentCode) 
    {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentCode() 
    {
        return equipmentCode;
    }
    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }
    public void setEquipmentModel(String equipmentModel) 
    {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentModel() 
    {
        return equipmentModel;
    }
    public void setEquipmentType(String equipmentType) 
    {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentType() 
    {
        return equipmentType;
    }
    public void setSupplier(String supplier) 
    {
        this.supplier = supplier;
    }

    public String getSupplier() 
    {
        return supplier;
    }
    public void setEquipmentStatus(String equipmentStatus) 
    {
        this.equipmentStatus = equipmentStatus;
    }

    public String getEquipmentStatus() 
    {
        return equipmentStatus;
    }
    public void setMainFunction(String mainFunction) 
    {
        this.mainFunction = mainFunction;
    }

    public String getMainFunction() 
    {
        return mainFunction;
    }
    public void setTechnicalSpecifications(String technicalSpecifications) 
    {
        this.technicalSpecifications = technicalSpecifications;
    }

    public String getTechnicalSpecifications() 
    {
        return technicalSpecifications;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("equipmentModuleId", getEquipmentModuleId())
            .append("equipmentInfoId", getEquipmentInfoId())
            .append("equipmentInfoName", getEquipmentInfoName())
            .append("equipmentCode", getEquipmentCode())
            .append("equipmentName", getEquipmentName())
            .append("equipmentModel", getEquipmentModel())
            .append("equipmentType", getEquipmentType())
            .append("supplier", getSupplier())
            .append("equipmentStatus", getEquipmentStatus())
            .append("mainFunction", getMainFunction())
            .append("technicalSpecifications", getTechnicalSpecifications())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
