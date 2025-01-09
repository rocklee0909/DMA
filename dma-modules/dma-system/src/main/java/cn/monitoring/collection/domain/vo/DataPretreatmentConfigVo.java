package cn.monitoring.collection.domain.vo;

import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 预处理规则配置对象Vo data_pretreatment_config
 * 
 * @author liru
 * @date 2025-01-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataPretreatmentConfigVo extends BaseEntity
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
    private Long[] pointIds;

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
}
