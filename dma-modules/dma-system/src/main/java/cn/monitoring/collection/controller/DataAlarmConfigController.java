package cn.monitoring.collection.controller;

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
import cn.monitoring.collection.domain.DataAlarmConfig;
import cn.monitoring.collection.service.IDataAlarmConfigService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 告警配置Controller
 * 
 * @author liru
 * @date 2025-01-03
 */
@RestController
@RequestMapping("/dataAlarmConfig")
public class DataAlarmConfigController extends BaseController
{
    @Autowired
    private IDataAlarmConfigService dataAlarmConfigService;

    /**
     * 查询告警配置列表
     */
    @RequiresPermissions("collection:dataAlarmConfig:list")
    @GetMapping("/list")
    public TableDataInfo list(DataAlarmConfig dataAlarmConfig)
    {
        startPage();
        List<DataAlarmConfig> list = dataAlarmConfigService.selectDataAlarmConfigList(dataAlarmConfig);
        return getDataTable(list);
    }

    /**
     * 导出告警配置列表
     */
    @RequiresPermissions("collection:dataAlarmConfig:export")
    @Log(title = "告警配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataAlarmConfig dataAlarmConfig)
    {
        List<DataAlarmConfig> list = dataAlarmConfigService.selectDataAlarmConfigList(dataAlarmConfig);
        ExcelUtil<DataAlarmConfig> util = new ExcelUtil<DataAlarmConfig>(DataAlarmConfig.class);
        util.exportExcel(response, list, "告警配置数据");
    }

    /**
     * 获取告警配置详细信息
     */
    @RequiresPermissions("collection:dataAlarmConfig:query")
    @GetMapping(value = "/{alertId}")
    public AjaxResult getInfo(@PathVariable("alertId") Long alertId)
    {
        return success(dataAlarmConfigService.selectDataAlarmConfigByAlertId(alertId));
    }

    /**
     * 新增告警配置
     */
    @RequiresPermissions("collection:dataAlarmConfig:add")
    @Log(title = "告警配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataAlarmConfig dataAlarmConfig)
    {
        return toAjax(dataAlarmConfigService.insertDataAlarmConfig(dataAlarmConfig));
    }

    /**
     * 修改告警配置
     */
    @RequiresPermissions("collection:dataAlarmConfig:edit")
    @Log(title = "告警配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataAlarmConfig dataAlarmConfig)
    {
        return toAjax(dataAlarmConfigService.updateDataAlarmConfig(dataAlarmConfig));
    }

    /**
     * 删除告警配置
     */
    @RequiresPermissions("collection:dataAlarmConfig:remove")
    @Log(title = "告警配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alertIds}")
    public AjaxResult remove(@PathVariable Long[] alertIds)
    {
        return toAjax(dataAlarmConfigService.deleteDataAlarmConfigByAlertIds(alertIds));
    }
}
