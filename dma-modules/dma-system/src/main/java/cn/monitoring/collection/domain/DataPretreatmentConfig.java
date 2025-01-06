package cn.monitoring.collection.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 预处理规则配置对象 data_pretreatment_config
 * 
 * @author liru
 * @date 2025-01-03
 */
public class DataPretreatmentConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规则id */
    private Long pretreatmentId;

    /** 工厂模型id */
    @Excel(name = "工厂模型id")
    private Long factoryModelId;

    /** 预处理名称 */
    @Excel(name = "预处理名称")
    private String pretreatmentName;

    /** 数据点id */
    @Excel(name = "数据点id")
    private Long pointId;

    /** 目标数据点名称 */
    @Excel(name = "目标数据点名称")
    private String targetDataPointName;

    /** 目标数据点编码 */
    @Excel(name = "目标数据点编码")
    private String targetDataPointCode;

    /** 预处理方法 */
    @Excel(name = "预处理方法")
    private Long pretreatmentMethod;

    /** 最新处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最新处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setPretreatmentId(Long pretreatmentId) 
    {
        this.pretreatmentId = pretreatmentId;
    }

    public Long getPretreatmentId() 
    {
        return pretreatmentId;
    }
    public void setFactoryModelId(Long factoryModelId) 
    {
        this.factoryModelId = factoryModelId;
    }

    public Long getFactoryModelId() 
    {
        return factoryModelId;
    }
    public void setPretreatmentName(String pretreatmentName) 
    {
        this.pretreatmentName = pretreatmentName;
    }

    public String getPretreatmentName() 
    {
        return pretreatmentName;
    }
    public void setPointId(Long pointId) 
    {
        this.pointId = pointId;
    }

    public Long getPointId() 
    {
        return pointId;
    }
    public void setTargetDataPointName(String targetDataPointName) 
    {
        this.targetDataPointName = targetDataPointName;
    }

    public String getTargetDataPointName() 
    {
        return targetDataPointName;
    }
    public void setTargetDataPointCode(String targetDataPointCode) 
    {
        this.targetDataPointCode = targetDataPointCode;
    }

    public String getTargetDataPointCode() 
    {
        return targetDataPointCode;
    }
    public void setPretreatmentMethod(Long pretreatmentMethod) 
    {
        this.pretreatmentMethod = pretreatmentMethod;
    }

    public Long getPretreatmentMethod() 
    {
        return pretreatmentMethod;
    }
    public void setHandleTime(Date handleTime) 
    {
        this.handleTime = handleTime;
    }

    public Date getHandleTime() 
    {
        return handleTime;
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
            .append("pretreatmentId", getPretreatmentId())
            .append("factoryModelId", getFactoryModelId())
            .append("pretreatmentName", getPretreatmentName())
            .append("pointId", getPointId())
            .append("targetDataPointName", getTargetDataPointName())
            .append("targetDataPointCode", getTargetDataPointCode())
            .append("pretreatmentMethod", getPretreatmentMethod())
            .append("handleTime", getHandleTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
