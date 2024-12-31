package cn.monitoring.process.service.impl;

import java.util.List;

import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.process.mapper.ProcessFlowMapper;
import cn.monitoring.process.domain.ProcessFlow;
import cn.monitoring.process.service.IProcessFlowService;

/**
 * 工艺流程信息Service业务层处理
 * 
 * @author liru
 * @date 2024-12-29
 */
@Service
public class ProcessFlowServiceImpl implements IProcessFlowService 
{
    @Autowired
    private ProcessFlowMapper processFlowMapper;

    /**
     * 查询工艺流程信息
     * 
     * @param flowId 工艺流程信息主键
     * @return 工艺流程信息
     */
    @Override
    public ProcessFlow selectProcessFlowByFlowId(Long flowId)
    {
        return processFlowMapper.selectProcessFlowByFlowId(flowId);
    }

    /**
     * 查询工艺流程信息列表
     * 
     * @param processFlow 工艺流程信息
     * @return 工艺流程信息
     */
    @Override
    public List<ProcessFlow> selectProcessFlowList(ProcessFlow processFlow)
    {
        return processFlowMapper.selectProcessFlowList(processFlow);
    }

    /**
     * 新增工艺流程信息
     * 
     * @param processFlow 工艺流程信息
     * @return 结果
     */
    @Override
    public int insertProcessFlow(ProcessFlow processFlow)
    {
        processFlow.setCreateBy(SecurityUtils.getUsername());
        processFlow.setCreateTime(DateUtils.getNowDate());
        return processFlowMapper.insertProcessFlow(processFlow);
    }

    /**
     * 修改工艺流程信息
     * 
     * @param processFlow 工艺流程信息
     * @return 结果
     */
    @Override
    public int updateProcessFlow(ProcessFlow processFlow)
    {
        processFlow.setUpdateBy(SecurityUtils.getUsername());
        processFlow.setUpdateTime(DateUtils.getNowDate());
        return processFlowMapper.updateProcessFlow(processFlow);
    }

    /**
     * 批量删除工艺流程信息
     * 
     * @param flowIds 需要删除的工艺流程信息主键
     * @return 结果
     */
    @Override
    public int deleteProcessFlowByFlowIds(Long[] flowIds)
    {
        return processFlowMapper.deleteProcessFlowByFlowIds(flowIds);
    }

    /**
     * 删除工艺流程信息信息
     * 
     * @param flowId 工艺流程信息主键
     * @return 结果
     */
    @Override
    public int deleteProcessFlowByFlowId(Long flowId)
    {
        return processFlowMapper.deleteProcessFlowByFlowId(flowId);
    }
}
