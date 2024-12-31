package cn.monitoring.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 工艺流程信息对象 process_flow
 * 
 * @author liru
 * @date 2024-12-29
 */
public class ProcessFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配方ID */
    private Long flowId;

    /** 工艺配方ID */
    @Excel(name = "工艺配方ID")
    private Long recipeId;

    /** 配方编号 */
    @Excel(name = "配方编号")
    private String recipeCode;

    /** 配方名称 */
    @Excel(name = "配方名称")
    private String recipeName;

    /** 步骤序号 */
    @Excel(name = "步骤序号")
    private Integer orderNum;

    /** 步骤名称 */
    @Excel(name = "步骤名称")
    private String flowName;

    /** 时间（s） */
    @Excel(name = "时间", readConverterExp = "s=")
    private Integer flowTime;

    public ProcessFlow() {
    }

    public ProcessFlow(Long recipeId){
        this.recipeId = recipeId;
    }

    public void setFlowId(Long flowId) 
    {
        this.flowId = flowId;
    }

    public Long getFlowId() 
    {
        return flowId;
    }
    public void setRecipeId(Long recipeId) 
    {
        this.recipeId = recipeId;
    }

    public Long getRecipeId() 
    {
        return recipeId;
    }
    public void setRecipeCode(String recipeCode) 
    {
        this.recipeCode = recipeCode;
    }

    public String getRecipeCode() 
    {
        return recipeCode;
    }
    public void setRecipeName(String recipeName) 
    {
        this.recipeName = recipeName;
    }

    public String getRecipeName() 
    {
        return recipeName;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }
    public void setFlowName(String flowName) 
    {
        this.flowName = flowName;
    }

    public String getFlowName() 
    {
        return flowName;
    }
    public void setFlowTime(Integer flowTime) 
    {
        this.flowTime = flowTime;
    }

    public Integer getFlowTime() 
    {
        return flowTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("flowId", getFlowId())
            .append("recipeId", getRecipeId())
            .append("recipeCode", getRecipeCode())
            .append("recipeName", getRecipeName())
            .append("orderNum", getOrderNum())
            .append("flowName", getFlowName())
            .append("flowTime", getFlowTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
