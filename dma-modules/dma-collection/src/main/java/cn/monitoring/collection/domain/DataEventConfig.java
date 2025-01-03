package cn.monitoring.collection.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 事件配置对象 data_event_config
 * 
 * @author liru
 * @date 2025-01-03
 */
public class DataEventConfig extends BaseEntity
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

    public void setEventId(Long eventId) 
    {
        this.eventId = eventId;
    }

    public Long getEventId() 
    {
        return eventId;
    }
    public void setFactoryModelId(Long factoryModelId) 
    {
        this.factoryModelId = factoryModelId;
    }

    public Long getFactoryModelId() 
    {
        return factoryModelId;
    }
    public void setEventName(String eventName) 
    {
        this.eventName = eventName;
    }

    public String getEventName() 
    {
        return eventName;
    }
    public void setTriggerCondition(String triggerCondition) 
    {
        this.triggerCondition = triggerCondition;
    }

    public String getTriggerCondition() 
    {
        return triggerCondition;
    }
    public void setLatestSerial(Long latestSerial) 
    {
        this.latestSerial = latestSerial;
    }

    public Long getLatestSerial() 
    {
        return latestSerial;
    }
    public void setCheckRate(Integer checkRate) 
    {
        this.checkRate = checkRate;
    }

    public Integer getCheckRate() 
    {
        return checkRate;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setCheckEquipmentNum(Integer checkEquipmentNum) 
    {
        this.checkEquipmentNum = checkEquipmentNum;
    }

    public Integer getCheckEquipmentNum() 
    {
        return checkEquipmentNum;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("eventId", getEventId())
            .append("factoryModelId", getFactoryModelId())
            .append("eventName", getEventName())
            .append("triggerCondition", getTriggerCondition())
            .append("latestSerial", getLatestSerial())
            .append("checkRate", getCheckRate())
            .append("checkTime", getCheckTime())
            .append("checkEquipmentNum", getCheckEquipmentNum())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
