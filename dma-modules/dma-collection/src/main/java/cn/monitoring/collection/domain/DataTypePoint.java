package cn.monitoring.collection.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;

/**
 * 数据类型点位关联对象 data_type_point
 * 
 * @author liru
 * @date 2024-12-31
 */
public class DataTypePoint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类型ID */
    private Long typeId;

    /** 点位ID */
    private Long pointId;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setPointId(Long pointId) 
    {
        this.pointId = pointId;
    }

    public Long getPointId() 
    {
        return pointId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("pointId", getPointId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
