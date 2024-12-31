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
import cn.monitoring.collection.domain.DataType;
import cn.monitoring.collection.service.IDataTypeService;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;

/**
 * 数据类型配置Controller
 * 
 * @author liru
 * @date 2024-12-30
 */
@RestController
@RequestMapping("/dataType")
public class DataTypeController extends BaseController
{
    @Autowired
    private IDataTypeService dataTypeService;

    /**
     * 查询数据类型配置列表
     */
    @RequiresPermissions("collection:dataType:list")
    @GetMapping("/list")
    public AjaxResult list(DataType dataType)
    {
        List<DataType> list = dataTypeService.selectDataTypeList(dataType);
        return success(list);
    }

    /**
     * 导出数据类型配置列表
     */
    @RequiresPermissions("collection:dataType:export")
    @Log(title = "数据类型配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataType dataType)
    {
        List<DataType> list = dataTypeService.selectDataTypeList(dataType);
        ExcelUtil<DataType> util = new ExcelUtil<DataType>(DataType.class);
        util.exportExcel(response, list, "数据类型配置数据");
    }

    /**
     * 获取数据类型配置详细信息
     */
    @RequiresPermissions("collection:dataType:query")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId)
    {
        return success(dataTypeService.selectDataTypeByTypeId(typeId));
    }

    /**
     * 新增数据类型配置
     */
    @RequiresPermissions("collection:dataType:add")
    @Log(title = "数据类型配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataType dataType)
    {
        return toAjax(dataTypeService.insertDataType(dataType));
    }

    /**
     * 修改数据类型配置
     */
    @RequiresPermissions("collection:dataType:edit")
    @Log(title = "数据类型配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataType dataType)
    {
        return toAjax(dataTypeService.updateDataType(dataType));
    }

    /**
     * 删除数据类型配置
     */
    @RequiresPermissions("collection:dataType:remove")
    @Log(title = "数据类型配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds)
    {
        return toAjax(dataTypeService.deleteDataTypeByTypeIds(typeIds));
    }
}
