package cn.monitoring.collection.domain.vo;

import cn.monitoring.collection.domain.CollectorInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Treeselect树结构实体类
 * 
 * @author liru
 */
@Data
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 节点对应的主题 **/
    private String dmaTopic;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(CollectorInfo collectorInfo)
    {
        this.id = collectorInfo.getCollectorId();
        this.label = collectorInfo.getCollectorName();
        this.dmaTopic = collectorInfo.getDmaTopic();
    }

}
