package cn.monitoring.process.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.monitoring.factory.domain.EquipmentInfo;
import cn.monitoring.factory.domain.FactoryModel;
import cn.monitoring.factory.service.IEquipmentInfoService;
import cn.monitoring.factory.service.IFactoryModelService;
import cn.monitoring.process.domain.vo.ProcessVo;
import cn.monitoring.process.service.IProcessFlowService;
import cn.monitoring.system.domain.vo.TreeGroupSelect;
import cn.monitoring.system.domain.vo.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.monitoring.process.domain.ProcessRecipe;
import cn.monitoring.process.service.IProcessRecipeService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 工艺配方信息Controller
 * 
 * @author liru
 * @date 2024-12-29
 */
@RestController
@RequestMapping("/processRecipe")
public class ProcessRecipeController extends BaseController
{
    private static final String TREE_FACTORY = "factory_";

    @Autowired
    private IProcessRecipeService processRecipeService;

    @Autowired
    private IProcessFlowService processFlowService;

    @Autowired
    private IFactoryModelService factoryModelService;

    @Autowired
    private IEquipmentInfoService equipmentInfoService;

    /**
     * 查询工艺配方信息列表
     */
    @RequiresPermissions("system:processRecipe:list")
    @GetMapping("/list")
    public TableDataInfo list(ProcessRecipe processRecipe)
    {
        startPage();
        List<ProcessRecipe> list = processRecipeService.selectProcessRecipeList(processRecipe);
        return getDataTable(list);
    }

    /**
     * 导出工艺配方信息列表
     */
    @RequiresPermissions("system:processRecipe:export")
    @Log(title = "工艺配方信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessRecipe processRecipe)
    {
        List<ProcessRecipe> list = processRecipeService.selectProcessRecipeList(processRecipe);
        ExcelUtil<ProcessRecipe> util = new ExcelUtil<>(ProcessRecipe.class);
        util.exportExcel(response, list, "工艺配方信息数据");
    }

    /**
     * 获取工艺配方信息详细信息
     */
    @RequiresPermissions("system:processRecipe:query")
    @GetMapping(value = "/{recipeId}")
    public AjaxResult getInfo(@PathVariable("recipeId") Long recipeId)
    {
        return success(processRecipeService.selectProcessRecipeByRecipeId(recipeId));
    }

    /**
     * 新增工艺配方信息
     */
    @RequiresPermissions("system:processRecipe:add")
    @Log(title = "工艺配方信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessVo processVo)
    {
        return toAjax(processRecipeService.insertProcessRecipe(processVo));
    }

    /**
     * 修改工艺配方信息
     */
    @RequiresPermissions("system:processRecipe:edit")
    @Log(title = "工艺配方信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessVo processVo)
    {
        return toAjax(processRecipeService.updateProcessRecipe(processVo));
    }

    /**
     * 删除工艺配方信息
     */
    @RequiresPermissions("system:processRecipe:remove")
    @Log(title = "工艺配方信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recipeIds}")
    public AjaxResult remove(@PathVariable Long[] recipeIds)
    {
        return toAjax(processRecipeService.deleteProcessRecipeByRecipeIds(recipeIds));
    }

    /**
     * 获取工厂模型树列表
     */
    @RequiresPermissions("system:processRecipe:list")
    @GetMapping("/factoryModelTree")
    public AjaxResult factoryModelTree(FactoryModel factoryModel)
    {
        List<TreeSelect> treeSelects = factoryModelService.selectModelTreeList(factoryModel);
        return success(convertTreeGroupSelect(treeSelects));
    }

    public List<TreeGroupSelect> convertTreeGroupSelect(List<TreeSelect> treeSelects){

        List<TreeGroupSelect> treeGroupSelects = new ArrayList<>();

        for(TreeSelect treeSelect:treeSelects){
            TreeGroupSelect treeGroupSelect = new TreeGroupSelect();
            treeGroupSelect.setId(TREE_FACTORY+treeSelect.getId());
            treeGroupSelect.setLabel(treeSelect.getLabel());
            treeGroupSelect.setIsClick(false);
            treeGroupSelect.setIsDisabled(true);

            List<TreeGroupSelect> childrenTreeGroupSelects = new ArrayList<>();
            if(treeSelect.getChildren()!=null&&treeSelect.getChildren().size()>0){
                childrenTreeGroupSelects =convertTreeGroupSelect(treeSelect.getChildren());
            }

            //加载设备信息到树节点
            List<EquipmentInfo> equipmentInfos = equipmentInfoService.selectEquipmentInfoList(new EquipmentInfo(treeSelect.getId()));
            for(EquipmentInfo equipmentInfo:equipmentInfos){
                TreeGroupSelect equipmentTreeGroupSelect = new TreeGroupSelect();
                equipmentTreeGroupSelect.setId(equipmentInfo.getEquipmentInfoId().toString());
                equipmentTreeGroupSelect.setLabel(equipmentInfo.getEquipmentName());
                equipmentTreeGroupSelect.setIsClick(true);
                equipmentTreeGroupSelect.setIsDisabled(false);
                childrenTreeGroupSelects.add(equipmentTreeGroupSelect);
            }

            treeGroupSelect.setChildren(childrenTreeGroupSelects);
            treeGroupSelects.add(treeGroupSelect);
        }

        return treeGroupSelects;
    }
}
