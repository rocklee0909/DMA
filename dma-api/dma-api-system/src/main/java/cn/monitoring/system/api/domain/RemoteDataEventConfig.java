package cn.monitoring.system.api.domain;

import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 事件配置对象 data_event_config
 * 
 * @author liru
 * @date 2025-01-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RemoteDataEventConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 事件id */
    private Long eventId;

    /** 工厂模型id */
    @Excel(name = "工厂模型id")
    private Long factoryModelId;

    /** 事件名称 */
    @Excel(name = "事件名称")
    private String eventName;

    /** 触发条件 */
    @Excel(name = "触发条件")
    private String triggerCondition;

    /** 最新序列号ms（最后检查数据毫秒值） */
    @Excel(name = "最新序列号ms", readConverterExp = "最=后检查数据毫秒值")
    private Long latestSerial;

    /** 检查频率 */
    @Excel(name = "检查频率")
    private Integer checkRate;

    /** 最新检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最新检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /** 检查设备数量 */
    @Excel(name = "检查设备数量")
    private Integer checkEquipmentNum;

    /** 事件状态（0停止 1启动） */
    @Excel(name = "事件状态", readConverterExp = "0=停止,1=启动")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
