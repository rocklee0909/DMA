package cn.monitoring.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 工艺配方信息对象 process_recipe
 * 
 * @author liru
 * @date 2024-12-29
 */
public class ProcessRecipe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配方ID */
    private Long recipeId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipmentInfoId;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 配方编号 */
    @Excel(name = "配方编号")
    private String recipeCode;

    /** 配方名称 */
    @Excel(name = "配方名称")
    private String recipeName;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String equipmentType;

    /** 配方状态 */
    @Excel(name = "配方状态")
    private String recipeStatus;

    public void setRecipeId(Long recipeId) 
    {
        this.recipeId = recipeId;
    }

    public Long getRecipeId() 
    {
        return recipeId;
    }
    public void setEquipmentInfoId(Long equipmentInfoId) 
    {
        this.equipmentInfoId = equipmentInfoId;
    }

    public Long getEquipmentInfoId() 
    {
        return equipmentInfoId;
    }
    public void setProductCode(String productCode) 
    {
        this.productCode = productCode;
    }

    public String getProductCode() 
    {
        return productCode;
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
    public void setEquipmentType(String equipmentType) 
    {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentType() 
    {
        return equipmentType;
    }
    public void setRecipeStatus(String recipeStatus) 
    {
        this.recipeStatus = recipeStatus;
    }

    public String getRecipeStatus() 
    {
        return recipeStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recipeId", getRecipeId())
            .append("equipmentInfoId", getEquipmentInfoId())
            .append("productCode", getProductCode())
            .append("recipeCode", getRecipeCode())
            .append("recipeName", getRecipeName())
            .append("equipmentType", getEquipmentType())
            .append("recipeStatus", getRecipeStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
