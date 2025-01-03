package cn.monitoring.collection.controller;

import java.util.List;
import java.io.IOException;
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
import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.service.ICollectorInfoService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等Controller
 * 
 * @author liru
 * @date 2024-12-15
 */
@RestController
@RequestMapping("/collectorInfo")
public class CollectorInfoController extends BaseController
{
    @Autowired
    private ICollectorInfoService collectorInfoService;

    /**
     * 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等列表
     */
    @RequiresPermissions("collection:collectorInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(CollectorInfo collectorInfo)
    {
        startPage();
        List<CollectorInfo> list = collectorInfoService.selectCollectorInfoList(collectorInfo);
        return getDataTable(list);
    }

    /**
     * 导出采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等列表
     */
    @RequiresPermissions("collection:collectorInfo:export")
    @Log(title = "采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CollectorInfo collectorInfo)
    {
        List<CollectorInfo> list = collectorInfoService.selectCollectorInfoList(collectorInfo);
        ExcelUtil<CollectorInfo> util = new ExcelUtil<CollectorInfo>(CollectorInfo.class);
        util.exportExcel(response, list, "采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等数据");
    }

    /**
     * 获取采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等详细信息
     */
    @RequiresPermissions("collection:collectorInfo:query")
    @GetMapping(value = "/{collectorId}")
    public AjaxResult getInfo(@PathVariable("collectorId") Long collectorId)
    {
        return success(collectorInfoService.selectCollectorInfoByCollectorId(collectorId));
    }

    /**
     * 新增采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     */
    @RequiresPermissions("collection:collectorInfo:add")
    @Log(title = "采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CollectorInfo collectorInfo)
    {
        if(collectorInfo.getEquipmentInfoId()==null){
            collectorInfo.setEquipmentInfoName("");
        }

        return toAjax(collectorInfoService.insertCollectorInfo(collectorInfo));
    }

    /**
     * 修改采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     */
    @RequiresPermissions("collection:collectorInfo:edit")
    @Log(title = "采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CollectorInfo collectorInfo)
    {
        if(collectorInfo.getEquipmentInfoId()==null){
            collectorInfo.setEquipmentInfoName("");
        }

        return toAjax(collectorInfoService.updateCollectorInfo(collectorInfo));
    }

    /**
     * 删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
     */
    @RequiresPermissions("collection:collectorInfo:remove")
    @Log(title = "采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等", businessType = BusinessType.DELETE)
	@DeleteMapping("/{collectorIds}")
    public AjaxResult remove(@PathVariable Long[] collectorIds)
    {
        return toAjax(collectorInfoService.deleteCollectorInfoByCollectorIds(collectorIds));
    }
}
