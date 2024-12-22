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
import cn.monitoring.collection.domain.DataPoint;
import cn.monitoring.collection.service.IDataPointService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 数据点位配置Controller
 * 
 * @author liru
 * @date 2024-12-20
 */
@RestController
@RequestMapping("/dataPoint")
public class DataPointController extends BaseController
{
    @Autowired
    private IDataPointService dataPointService;

    /**
     * 查询数据点位配置列表
     */
    @RequiresPermissions("collection:dataPoint:list")
    @GetMapping("/list")
    public TableDataInfo list(DataPoint dataPoint)
    {
        startPage();
        List<DataPoint> list = dataPointService.selectDataPointList(dataPoint);
        return getDataTable(list);
    }

    /**
     * 导出数据点位配置列表
     */
    @RequiresPermissions("collection:dataPoint:export")
    @Log(title = "数据点位配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataPoint dataPoint)
    {
        List<DataPoint> list = dataPointService.selectDataPointList(dataPoint);
        ExcelUtil<DataPoint> util = new ExcelUtil<DataPoint>(DataPoint.class);
        util.exportExcel(response, list, "数据点位配置数据");
    }

    /**
     * 获取数据点位配置详细信息
     */
    @RequiresPermissions("collection:dataPoint:query")
    @GetMapping(value = "/{pointId}")
    public AjaxResult getInfo(@PathVariable("pointId") Long pointId)
    {
        return success(dataPointService.selectDataPointByPointId(pointId));
    }

    /**
     * 新增数据点位配置
     */
    @RequiresPermissions("collection:dataPoint:add")
    @Log(title = "数据点位配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataPoint dataPoint)
    {
        return toAjax(dataPointService.insertDataPoint(dataPoint));
    }

    /**
     * 修改数据点位配置
     */
    @RequiresPermissions("collection:dataPoint:edit")
    @Log(title = "数据点位配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataPoint dataPoint)
    {
        return toAjax(dataPointService.updateDataPoint(dataPoint));
    }

    /**
     * 删除数据点位配置
     */
    @RequiresPermissions("collection:dataPoint:remove")
    @Log(title = "数据点位配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pointIds}")
    public AjaxResult remove(@PathVariable Long[] pointIds)
    {
        return toAjax(dataPointService.deleteDataPointByPointIds(pointIds));
    }
}
