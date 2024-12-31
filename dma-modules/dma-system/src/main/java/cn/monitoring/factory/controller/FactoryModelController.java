package cn.monitoring.factory.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import cn.monitoring.common.core.constant.UserConstants;
import cn.monitoring.common.core.utils.StringUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.monitoring.common.log.annotation.Log;
import cn.monitoring.common.log.enums.BusinessType;
import cn.monitoring.common.security.annotation.RequiresPermissions;
import cn.monitoring.factory.domain.FactoryModel;
import cn.monitoring.factory.service.IFactoryModelService;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;

/**
 * 工厂建模Controller
 * 
 * @author liru
 * @date 2024-12-27
 */
@RestController
@RequestMapping("/factorymodel")
public class FactoryModelController extends BaseController
{
    @Autowired
    private IFactoryModelService factoryModelService;

    /**
     * 查询工厂建模列表
     */
    @RequiresPermissions("system:factorymodel:list")
    @GetMapping("/list")
    public AjaxResult list(FactoryModel factoryModel)
    {
        List<FactoryModel> list = factoryModelService.selectFactoryModelList(factoryModel);
        return success(list);
    }

    /**
     * 导出工厂建模列表
     */
    @RequiresPermissions("system:factorymodel:export")
    @Log(title = "工厂建模", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FactoryModel factoryModel)
    {
        List<FactoryModel> list = factoryModelService.selectFactoryModelList(factoryModel);
        ExcelUtil<FactoryModel> util = new ExcelUtil<>(FactoryModel.class);
        util.exportExcel(response, list, "工厂建模数据");
    }

    /**
     * 获取工厂建模详细信息
     */
    @RequiresPermissions("system:factorymodel:query")
    @GetMapping(value = "/{modelId}")
    public AjaxResult getInfo(@PathVariable("modelId") Long modelId)
    {
        return success(factoryModelService.selectFactoryModelByModelId(modelId));
    }

    /**
     * 新增工厂建模
     */
    @RequiresPermissions("system:factorymodel:add")
    @Log(title = "工厂建模", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody FactoryModel factoryModel)
    {
        factoryModel.setCreateBy(SecurityUtils.getUsername());
        return toAjax(factoryModelService.insertFactoryModel(factoryModel));
    }

    /**
     * 修改工厂建模
     */
    @RequiresPermissions("system:factorymodel:edit")
    @Log(title = "工厂建模", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody FactoryModel factoryModel)
    {

        Long modelId = factoryModel.getModelId();

        if (factoryModel.getParentId().equals(modelId))
        {
            return error("修改部门'" + factoryModel.getModelName() + "'失败，上级工厂模型不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, factoryModel.getStatus()) && factoryModelService.selectNormalChildrenModelById(modelId) > 0)
        {
            return error("该工厂模型包含未停用的子模型！");
        }
        factoryModel.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(factoryModelService.updateFactoryModel(factoryModel));
    }

    /**
     * 删除工厂建模
     */
    @RequiresPermissions("system:factorymodel:remove")
    @Log(title = "工厂建模", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelIds}")
    public AjaxResult remove(@PathVariable Long[] modelIds)
    {
        for (Long modelId : modelIds){
            if (factoryModelService.hasChildByModelId(modelId))
            {
                return warn("存在下级工厂模型,不允许删除");
            }
        }

        return toAjax(factoryModelService.deleteFactoryModelByModelIds(modelIds));
    }

    /**
     * 获取工厂模型树列表
     */
    @RequiresPermissions("system:factorymodel:list")
    @GetMapping("/factorymodelTree")
    public AjaxResult factorymodelTree(FactoryModel factoryModel)
    {
        return success(factoryModelService.selectModelTreeList(factoryModel));
    }
}
