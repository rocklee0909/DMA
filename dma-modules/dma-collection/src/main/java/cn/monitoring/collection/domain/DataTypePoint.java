package cn.monitoring.collection.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataTypePoint extends DataPoint
{
    private static final long serialVersionUID = 1L;

    /** 类型ID */
    private Long dataTypeId;

    /** 点位ID */
    private Long dataPointId;

}
