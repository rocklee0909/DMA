package cn.monitoring.factory.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 设备基本信息对象 equipment_info
 * 
 * @author liru
 * @date 2024-12-27
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class EquipmentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long equipmentInfoId;

    /** 工厂模型ID */
    @Excel(name = "工厂模型ID")
    private Long factoryModelId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String equipmentCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String equipmentModel;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String equipmentType;

    /** 子设备数 */
    @Excel(name = "子设备数")
    private Long childEquipmentNum;

    /** 设备供应商 */
    @Excel(name = "设备供应商")
    private String supplier;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String equipmentStatus;

    /** 设备描述 */
    @Excel(name = "设备描述")
    private String equipmentDescription;

    /** 设备主要功能 */
    @Excel(name = "设备主要功能")
    private String mainFunction;

    /** 设备技术规格 */
    @Excel(name = "设备技术规格")
    private String technicalSpecifications;

    public EquipmentInfo(Long factoryModelId) {
        this.factoryModelId = factoryModelId;
    }

}
