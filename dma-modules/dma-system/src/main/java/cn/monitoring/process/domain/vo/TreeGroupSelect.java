package cn.monitoring.process.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * Treeselect组合树结构实体类
 * 
 * @author liru
 */
@Data
public class TreeGroupSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;
    /** 节点名称 */
    private String label;
    /** 节点编码 */
    private String code;

    private Boolean isClick = false;

    private Boolean isDisabled = true;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeGroupSelect> children;
}
