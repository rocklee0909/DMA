package cn.monitoring.process.mapper;

import java.util.List;
import cn.monitoring.process.domain.ProcessFlow;

/**
 * 工艺流程信息Mapper接口
 * 
 * @author liru
 * @date 2024-12-29
 */
public interface ProcessFlowMapper 
{
    /**
     * 查询工艺流程信息
     * 
     * @param flowId 工艺流程信息主键
     * @return 工艺流程信息
     */
    public ProcessFlow selectProcessFlowByFlowId(Long flowId);

    /**
     * 查询工艺流程信息列表
     * 
     * @param processFlow 工艺流程信息
     * @return 工艺流程信息集合
     */
    public List<ProcessFlow> selectProcessFlowList(ProcessFlow processFlow);

    /**
     * 新增工艺流程信息
     * 
     * @param processFlow 工艺流程信息
     * @return 结果
     */
    public int insertProcessFlow(ProcessFlow processFlow);

    /**
     * 修改工艺流程信息
     * 
     * @param processFlow 工艺流程信息
     * @return 结果
     */
    public int updateProcessFlow(ProcessFlow processFlow);

    /**
     * 删除工艺流程信息
     * 
     * @param flowId 工艺流程信息主键
     * @return 结果
     */
    public int deleteProcessFlowByFlowId(Long flowId);

    /**
     * 批量删除工艺流程信息
     * 
     * @param flowIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessFlowByFlowIds(Long[] flowIds);
}
