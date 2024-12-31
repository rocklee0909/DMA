package cn.monitoring.factory.domain;

import cn.monitoring.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.monitoring.common.core.annotation.Excel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 工厂建模对象 factory_model
 * 
 * @author liru
 * @date 2024-12-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FactoryModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模型id */
    private Long modelId;

    /** 模型名称 */
    @Excel(name = "模型名称")
    private String modelName;

    /** 模型编码 */
    @Excel(name = "模型编码")
    private String modelCode;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;

    /** 父菜单ID */
    @Excel(name = "父菜单ID")
    private Long parentId;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 模型名称 */
    @Excel(name = "模型名称")
    private String modelType;

    /** 模型状态（0正常 1停用） */
    @Excel(name = "模型状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @Excel(name = "删除标志", readConverterExp = "0=存在,2=删除")
    private String delFlag;

    private List<FactoryModel> children = new ArrayList<FactoryModel>();


    @NotBlank(message = "模型名称不能为空")
    @Size(min = 0, max = 30, message = "模型名称长度不能超过30个字符")
    public String getModelName(){
        return this.modelName;
    }

    @NotNull(message = "显示顺序不能为空")
    public Integer getOrderNum()
    {
        return orderNum;
    }

}
