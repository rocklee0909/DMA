package cn.monitoring.collection.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等对象 collector_info
 * 
 * @author liru
 * @date 2024-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CollectorInfo extends BaseEntity
{
    /** ID */
    private Long collectorId;

    /** 设备信息ID */
    private String equipmentInfoId;

    /** 设备信息名称 */
    @Excel(name = "归属设备名称")
    private String equipmentInfoName;

    /** 采集器名称 */
    @Excel(name = "采集器名称")
    private String collectorName;

    /** 采集器类型 */
    @Excel(name = "采集器类型")
    private String collectorType;

    /** 模块名称 */
    @Excel(name = "模块名称")
    private String moduleName;

    /** 详细描述 */
    @Excel(name = "详细描述")
    private String description;

    /** 组名称 */
    @Excel(name = "组名称")
    private String dmaGroup;

    /** 主题 */
    @Excel(name = "主题")
    private String dmaTopic;

    /** 限制数据点接收数 */
    @Excel(name = "限制数据点接收数")
    private Integer limitDataNum;

    /** 有效数据点数量 */
    @Excel(name = "有效数据点数量")
    private Integer validDataNum;
}
