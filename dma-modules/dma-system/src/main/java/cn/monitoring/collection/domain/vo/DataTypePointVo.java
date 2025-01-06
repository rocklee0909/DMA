package cn.monitoring.collection.domain.vo;

import lombok.Data;

/**
 * 数据类型点位关联Vo
 */
@Data
public class DataTypePointVo {
    private Long[] typeIds;
    private Long[] pointIds;
}
