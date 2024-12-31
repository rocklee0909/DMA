package cn.monitoring.process.controller;

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
import cn.monitoring.process.domain.ProcessFlow;
import cn.monitoring.process.service.IProcessFlowService;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.core.web.controller.BaseController;
import cn.monitoring.common.core.web.domain.AjaxResult;
import cn.monitoring.common.core.utils.poi.ExcelUtil;
import cn.monitoring.common.core.web.page.TableDataInfo;

/**
 * 工艺流程信息Controller
 * 
 * @author liru
 * @date 2024-12-29
 */
@RestController
@RequestMapping("/processFlow")
public class ProcessFlowController extends BaseController
{
    @Autowired
    private IProcessFlowService processFlowService;

    /**
     * 查询工艺流程信息列表
     */
    @RequiresPermissions("system:processFlow:list")
    @GetMapping("/list")
    public TableDataInfo list(ProcessFlow processFlow)
    {
        startPage();
        List<ProcessFlow> list = processFlowService.selectProcessFlowList(processFlow);
        return getDataTable(list);
    }

    /**
     * 导出工艺流程信息列表
     */
    @RequiresPermissions("system:processFlow:export")
    @Log(title = "工艺流程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessFlow processFlow)
    {
        List<ProcessFlow> list = processFlowService.selectProcessFlowList(processFlow);
        ExcelUtil<ProcessFlow> util = new ExcelUtil<ProcessFlow>(ProcessFlow.class);
        util.exportExcel(response, list, "工艺流程信息数据");
    }

    /**
     * 获取工艺流程信息详细信息
     */
    @RequiresPermissions("system:processFlow:query")
    @GetMapping(value = "/{flowId}")
    public AjaxResult getInfo(@PathVariable("flowId") Long flowId)
    {
        return success(processFlowService.selectProcessFlowByFlowId(flowId));
    }

    /**
     * 新增工艺流程信息
     */
    @RequiresPermissions("system:processFlow:add")
    @Log(title = "工艺流程信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessFlow processFlow)
    {
        return toAjax(processFlowService.insertProcessFlow(processFlow));
    }

    /**
     * 修改工艺流程信息
     */
    @RequiresPermissions("system:processFlow:edit")
    @Log(title = "工艺流程信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessFlow processFlow)
    {
        return toAjax(processFlowService.updateProcessFlow(processFlow));
    }

    /**
     * 删除工艺流程信息
     */
    @RequiresPermissions("system:processFlow:remove")
    @Log(title = "工艺流程信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{flowIds}")
    public AjaxResult remove(@PathVariable Long[] flowIds)
    {
        return toAjax(processFlowService.deleteProcessFlowByFlowIds(flowIds));
    }
}
