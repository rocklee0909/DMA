package cn.monitoring.factory.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import cn.monitoring.factory.domain.EquipmentModule;
import cn.monitoring.factory.service.IEquipmentModuleService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 设备模块信息-子设备Controller
 * 
 * @author liru
 * @date 2024-12-28
 */
@RestController
@RequestMapping("/equipmentModule")
public class EquipmentModuleController extends BaseController
{
    @Autowired
    private IEquipmentModuleService equipmentModuleService;

    /**
     * 查询设备模块信息-子设备列表
     */
    @RequiresPermissions("system:equipmentModule:list")
    @GetMapping("/list")
    public TableDataInfo list(EquipmentModule equipmentModule)
    {
        startPage();
        List<EquipmentModule> list = equipmentModuleService.selectEquipmentModuleList(equipmentModule);
        return getDataTable(list);
    }

    /**
     * 导出设备模块信息-子设备列表
     */
    @RequiresPermissions("system:equipmentModule:export")
    @Log(title = "设备模块信息-子设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentModule equipmentModule)
    {
        List<EquipmentModule> list = equipmentModuleService.selectEquipmentModuleList(equipmentModule);
        ExcelUtil<EquipmentModule> util = new ExcelUtil<>(EquipmentModule.class);
        util.exportExcel(response, list, "设备模块信息-子设备数据");
    }

    /**
     * 获取设备模块信息-子设备详细信息
     */
    @RequiresPermissions("system:equipmentModule:query")
    @GetMapping(value = "/{equipmentModuleId}")
    public AjaxResult getInfo(@PathVariable("equipmentModuleId") Long equipmentModuleId)
    {
        return success(equipmentModuleService.selectEquipmentModuleByEquipmentModuleId(equipmentModuleId));
    }

    /**
     * 新增设备模块信息-子设备
     */
    @RequiresPermissions("system:equipmentModule:add")
    @Log(title = "设备模块信息-子设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentModule equipmentModule)
    {
        return toAjax(equipmentModuleService.insertEquipmentModule(equipmentModule));
    }

    /**
     * 修改设备模块信息-子设备
     */
    @RequiresPermissions("system:equipmentModule:edit")
    @Log(title = "设备模块信息-子设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentModule equipmentModule)
    {
        return toAjax(equipmentModuleService.updateEquipmentModule(equipmentModule));
    }

    /**
     * 删除设备模块信息-子设备
     */
    @RequiresPermissions("system:equipmentModule:remove")
    @Log(title = "设备模块信息-子设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{equipmentModuleIds}")
    public AjaxResult remove(@PathVariable Long[] equipmentModuleIds)
    {
        return toAjax(equipmentModuleService.deleteEquipmentModuleByEquipmentModuleIds(equipmentModuleIds));
    }
}
