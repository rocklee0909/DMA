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
import cn.monitoring.collection.domain.DataPretreatmentConfig;
import cn.monitoring.collection.service.IDataPretreatmentConfigService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 预处理规则配置Controller
 * 
 * @author liru
 * @date 2025-01-03
 */
@RestController
@RequestMapping("/dataPretreatmentConfig")
public class DataPretreatmentConfigController extends BaseController
{
    @Autowired
    private IDataPretreatmentConfigService dataPretreatmentConfigService;

    /**
     * 查询预处理规则配置列表
     */
    @RequiresPermissions("collection:dataPretreatmentConfig:list")
    @GetMapping("/list")
    public TableDataInfo list(DataPretreatmentConfig dataPretreatmentConfig)
    {
        startPage();
        List<DataPretreatmentConfig> list = dataPretreatmentConfigService.selectDataPretreatmentConfigList(dataPretreatmentConfig);
        return getDataTable(list);
    }

    /**
     * 导出预处理规则配置列表
     */
    @RequiresPermissions("collection:dataPretreatmentConfig:export")
    @Log(title = "预处理规则配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataPretreatmentConfig dataPretreatmentConfig)
    {
        List<DataPretreatmentConfig> list = dataPretreatmentConfigService.selectDataPretreatmentConfigList(dataPretreatmentConfig);
        ExcelUtil<DataPretreatmentConfig> util = new ExcelUtil<DataPretreatmentConfig>(DataPretreatmentConfig.class);
        util.exportExcel(response, list, "预处理规则配置数据");
    }

    /**
     * 获取预处理规则配置详细信息
     */
    @RequiresPermissions("collection:dataPretreatmentConfig:query")
    @GetMapping(value = "/{pretreatmentId}")
    public AjaxResult getInfo(@PathVariable("pretreatmentId") Long pretreatmentId)
    {
        return success(dataPretreatmentConfigService.selectDataPretreatmentConfigByPretreatmentId(pretreatmentId));
    }

    /**
     * 新增预处理规则配置
     */
    @RequiresPermissions("collection:dataPretreatmentConfig:add")
    @Log(title = "预处理规则配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataPretreatmentConfig dataPretreatmentConfig)
    {
        return toAjax(dataPretreatmentConfigService.insertDataPretreatmentConfig(dataPretreatmentConfig));
    }

    /**
     * 修改预处理规则配置
     */
    @RequiresPermissions("collection:dataPretreatmentConfig:edit")
    @Log(title = "预处理规则配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataPretreatmentConfig dataPretreatmentConfig)
    {
        return toAjax(dataPretreatmentConfigService.updateDataPretreatmentConfig(dataPretreatmentConfig));
    }

    /**
     * 删除预处理规则配置
     */
    @RequiresPermissions("collection:dataPretreatmentConfig:remove")
    @Log(title = "预处理规则配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pretreatmentIds}")
    public AjaxResult remove(@PathVariable Long[] pretreatmentIds)
    {
        return toAjax(dataPretreatmentConfigService.deleteDataPretreatmentConfigByPretreatmentIds(pretreatmentIds));
    }
}
