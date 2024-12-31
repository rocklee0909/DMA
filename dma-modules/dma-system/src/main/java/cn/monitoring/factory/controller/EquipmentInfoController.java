package cn.monitoring.factory.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.monitoring.factory.domain.FactoryModel;
import cn.monitoring.factory.domain.vo.EquipmentVo;
import cn.monitoring.factory.service.IFactoryModelService;
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
import cn.monitoring.factory.domain.EquipmentInfo;
import cn.monitoring.factory.service.IEquipmentInfoService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 设备基本信息Controller
 * 
 * @author liru
 * @date 2024-12-27
 */
@RestController
@RequestMapping("/equipmentInfo")
public class EquipmentInfoController extends BaseController
{
    @Autowired
    private IEquipmentInfoService equipmentInfoService;

    @Autowired
    private IFactoryModelService factoryModelService;

    /**
     * 查询设备基本信息列表
     */
    @RequiresPermissions("system:equipmentInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(EquipmentInfo equipmentInfo)
    {
        startPage();
        List<EquipmentInfo> list = equipmentInfoService.selectEquipmentInfoList(equipmentInfo);
        return getDataTable(list);
    }

    /**
     * 导出设备基本信息列表
     */
    @RequiresPermissions("system:equipmentInfo:export")
    @Log(title = "设备基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentInfo equipmentInfo)
    {
        List<EquipmentInfo> list = equipmentInfoService.selectEquipmentInfoList(equipmentInfo);
        ExcelUtil<EquipmentInfo> util = new ExcelUtil<EquipmentInfo>(EquipmentInfo.class);
        util.exportExcel(response, list, "设备基本信息数据");
    }

    /**
     * 获取设备基本信息详细信息
     */
    @RequiresPermissions("system:equipmentInfo:query")
    @GetMapping(value = "/{equipmentInfoId}")
    public AjaxResult getInfo(@PathVariable("equipmentInfoId") Long equipmentInfoId)
    {
        return success(equipmentInfoService.selectEquipmentInfoByEquipmentInfoId(equipmentInfoId));
    }

    /**
     * 新增设备基本信息
     */
    @RequiresPermissions("system:equipmentInfo:add")
    @Log(title = "设备基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentVo equipmentVo)
    {
        return toAjax(equipmentInfoService.insertEquipmentInfo(equipmentVo));
    }

    /**
     * 修改设备基本信息
     */
    @RequiresPermissions("system:equipmentInfo:edit")
    @Log(title = "设备基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentVo equipmentVo)
    {
        return toAjax(equipmentInfoService.updateEquipmentInfo(equipmentVo));
    }

    /**
     * 删除设备基本信息
     */
    @RequiresPermissions("system:equipmentInfo:remove")
    @Log(title = "设备基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{equipmentInfoIds}")
    public AjaxResult remove(@PathVariable Long[] equipmentInfoIds)
    {
        return toAjax(equipmentInfoService.deleteEquipmentInfoByEquipmentInfoIds(equipmentInfoIds));
    }

    /**
     * 获取工厂模型树列表
     */
    @RequiresPermissions("system:equipmentInfo:list")
    @GetMapping("/factoryModelTree")
    public AjaxResult factoryModelTree(FactoryModel factoryModel)
    {
        return success(factoryModelService.selectModelTreeList(factoryModel));
    }

}
