package cn.monitoring.collection.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.monitoring.common.core.annotation.Excel;
import cn.monitoring.common.core.web.domain.BaseEntity;
import lombok.NoArgsConstructor;

/**
 * 数据点位配置对象 data_point
 * 
 * @author liru
 * @date 2024-12-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataPoint extends BaseEntity
{
    /** ID */
    private Long pointId;

    /** 采集器ID */
    @Excel(name = "采集器ID")
    private Long collectorId;

    /** 主题 */
    @Excel(name = "主题")
    private String dmaTopic;

    /** 表名 */
    @Excel(name = "表名")
    private String tableName;

    /** 数据点位名称：方便识别和引用 */
    @Excel(name = "数据点位名称：方便识别和引用")
    private String pointName;

    /** 数据点位编码：用于与外部系统对接等，具有唯一性 */
    @Excel(name = "数据点位编码：用于与外部系统对接等，具有唯一性")
    private String pointCode;

    /** 数据点位详细描述：例如功能、采集范围等 */
    @Excel(name = "数据点位详细描述：例如功能、采集范围等")
    private String description;

    /** 数据类型 */
    @Excel(name = "数据类型")
    private String dataType;

    /** 数据单位：如温度的“℃”等 */
    @Excel(name = "数据单位：如温度的“℃”等")
    private String unit;

    /** 点位是否处于激活可用状态 */
    @Excel(name = "点位是否处于激活可用状态")
    private Integer isActive;

    /**值**/
    private Object value;
}
