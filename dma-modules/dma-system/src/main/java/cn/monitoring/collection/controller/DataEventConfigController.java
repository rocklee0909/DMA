package cn.monitoring.collection.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.monitoring.common.core.domain.R;
import cn.monitoring.system.api.domain.RemoteDataEventConfig;
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
import cn.monitoring.collection.domain.DataEventConfig;
import cn.monitoring.collection.service.IDataEventConfigService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 事件配置Controller
 * 
 * @author liru
 * @date 2025-01-03
 */
@RestController
@RequestMapping("/dataEventConfig")
public class DataEventConfigController extends BaseController
{
    @Autowired
    private IDataEventConfigService dataEventConfigService;

    /**
     * 查询事件配置列表
     */
    @RequiresPermissions("collection:dataEventConfig:list")
    @GetMapping("/list")
    public TableDataInfo list(DataEventConfig dataEventConfig)
    {
        startPage();
        List<DataEventConfig> list = dataEventConfigService.selectDataEventConfigList(dataEventConfig);
        return getDataTable(list);
    }

    /**
     * 导出事件配置列表
     */
    @RequiresPermissions("collection:dataEventConfig:export")
    @Log(title = "事件配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataEventConfig dataEventConfig)
    {
        List<DataEventConfig> list = dataEventConfigService.selectDataEventConfigList(dataEventConfig);
        ExcelUtil<DataEventConfig> util = new ExcelUtil<DataEventConfig>(DataEventConfig.class);
        util.exportExcel(response, list, "事件配置数据");
    }

    /**
     * 获取事件配置详细信息
     */
    @RequiresPermissions("collection:dataEventConfig:query")
    @GetMapping(value = "/{eventId}")
    public AjaxResult getInfo(@PathVariable("eventId") Long eventId)
    {
        return success(dataEventConfigService.selectDataEventConfigByEventId(eventId));
    }

    /**
     * 新增事件配置
     */
    @RequiresPermissions("collection:dataEventConfig:add")
    @Log(title = "事件配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataEventConfig dataEventConfig)
    {
        return toAjax(dataEventConfigService.insertDataEventConfig(dataEventConfig));
    }

    /**
     * 修改事件配置
     */
    @RequiresPermissions("collection:dataEventConfig:edit")
    @Log(title = "事件配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataEventConfig dataEventConfig)
    {
        return toAjax(dataEventConfigService.updateDataEventConfig(dataEventConfig));
    }

    /**
     * 修改事件配置
     */
    @Log(title = "事件配置更新", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public R<RemoteDataEventConfig> update(@RequestBody RemoteDataEventConfig dataEventConfig)
    {
        dataEventConfigService.updateDataEventConfig((DataEventConfig) dataEventConfig);
        return R.ok(dataEventConfig);
    }

    /**
     * 删除事件配置
     */
    @RequiresPermissions("collection:dataEventConfig:remove")
    @Log(title = "事件配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{eventIds}")
    public AjaxResult remove(@PathVariable Long[] eventIds)
    {
        return toAjax(dataEventConfigService.deleteDataEventConfigByEventIds(eventIds));
    }

}
