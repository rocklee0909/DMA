package cn.monitoring.collection.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.TreeEntity;

/**
 * 数据类型配置对象 data_type
 * 
 * @author liru
 * @date 2024-12-30
 */
public class DataType extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Long typeId;

    /** 类别名称 */
    @Excel(name = "类别名称")
    private String typeName;

    /** 类型编码 */
    @Excel(name = "类型编码")
    private String typeCode;

    /** 类别组 */
    @Excel(name = "类别组")
    private String typeGroup;

    /** 关联数量 */
    @Excel(name = "关联数量")
    private Integer dataNum;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setTypeCode(String typeCode) 
    {
        this.typeCode = typeCode;
    }

    public String getTypeCode() 
    {
        return typeCode;
    }
    public void setTypeGroup(String typeGroup) 
    {
        this.typeGroup = typeGroup;
    }

    public String getTypeGroup() 
    {
        return typeGroup;
    }
    public void setDataNum(Integer dataNum) 
    {
        this.dataNum = dataNum;
    }

    public Integer getDataNum() 
    {
        return dataNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("typeName", getTypeName())
            .append("typeCode", getTypeCode())
            .append("orderNum", getOrderNum())
            .append("typeGroup", getTypeGroup())
            .append("dataNum", getDataNum())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
