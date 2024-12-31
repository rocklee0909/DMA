package cn.monitoring.collection.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import cn.monitoring.collection.domain.CollectorInfo;
import cn.monitoring.collection.jobs.CollectorInfoDataReceiveJob;
import cn.monitoring.collection.service.ICollectorInfoService;
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
import cn.monitoring.collection.domain.DataTypePoint;
import cn.monitoring.collection.service.IDataTypePointService;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 数据类型点位关联Controller
 * 
 * @author liru
 * @date 2024-12-31
 */
@RestController
@RequestMapping("/dataTypePoint")
public class DataTypePointController extends BaseController
{
    @Autowired
    private IDataTypePointService dataTypePointService;
    @Autowired
    private ICollectorInfoService collectorInfoService;

    /**
     * 查询数据类型点位关联列表
     */
    @RequiresPermissions("collection:dataTypePoint:list")
    @GetMapping("/list")
    public TableDataInfo list(DataTypePoint dataTypePoint)
    {
        startPage();
        List<DataTypePoint> list = dataTypePointService.selectDataTypePointList(dataTypePoint);
        return getDataTable(list);
    }

    /**
     * 导出数据类型点位关联列表
     */
    @RequiresPermissions("collection:dataTypePoint:export")
    @Log(title = "数据类型点位关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataTypePoint dataTypePoint)
    {
        List<DataTypePoint> list = dataTypePointService.selectDataTypePointList(dataTypePoint);
        ExcelUtil<DataTypePoint> util = new ExcelUtil<DataTypePoint>(DataTypePoint.class);
        util.exportExcel(response, list, "数据类型点位关联数据");
    }

    /**
     * 获取数据类型点位关联详细信息
     */
    @RequiresPermissions("collection:dataTypePoint:query")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId)
    {
        return success(dataTypePointService.selectDataTypePointByTypeId(typeId));
    }

    /**
     * 新增数据类型点位关联
     */
    @RequiresPermissions("collection:dataTypePoint:add")
    @Log(title = "数据类型点位关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataTypePoint dataTypePoint)
    {
        return toAjax(dataTypePointService.insertDataTypePoint(dataTypePoint));
    }

    /**
     * 修改数据类型点位关联
     */
    @RequiresPermissions("collection:dataTypePoint:edit")
    @Log(title = "数据类型点位关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataTypePoint dataTypePoint)
    {
        return toAjax(dataTypePointService.updateDataTypePoint(dataTypePoint));
    }

    /**
     * 删除数据类型点位关联
     */
    @RequiresPermissions("collection:dataTypePoint:remove")
    @Log(title = "数据类型点位关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds)
    {
        return toAjax(dataTypePointService.deleteDataTypePointByTypeIds(typeIds));
    }

    /**
     * 获取采集器树列表
     */
    @RequiresPermissions("collection:dataTypePoint:list")
    @GetMapping("/collectorTree")
    public AjaxResult collectorTree(CollectorInfo collectorInfo)
    {
        return success(collectorInfoService.selectCollectorTreeList(collectorInfo));
    }
}
