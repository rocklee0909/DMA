package cn.monitoring.process.service.impl;

import java.util.List;

import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import cn.monitoring.factory.domain.EquipmentModule;
import cn.monitoring.process.domain.ProcessFlow;
import cn.monitoring.process.domain.vo.ProcessVo;
import cn.monitoring.process.mapper.ProcessFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.process.mapper.ProcessRecipeMapper;
import cn.monitoring.process.domain.ProcessRecipe;
import cn.monitoring.process.service.IProcessRecipeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工艺配方信息Service业务层处理
 * 
 * @author liru
 * @date 2024-12-29
 */
@Service
public class ProcessRecipeServiceImpl implements IProcessRecipeService 
{
    @Autowired
    private ProcessRecipeMapper processRecipeMapper;

    @Autowired
    private ProcessFlowMapper processFlowMapper;

    /**
     * 查询工艺配方信息
     *
     * @param recipeId 工艺配方信息主键
     * @return 工艺配方信息
     */
    @Override
    public ProcessVo selectProcessRecipeByRecipeId(Long recipeId)
    {
        ProcessRecipe processRecipe = processRecipeMapper.selectProcessRecipeByRecipeId(recipeId);
        List<ProcessFlow> equipmentModules = processFlowMapper.selectProcessFlowList(new ProcessFlow(recipeId));
        return new ProcessVo(processRecipe,equipmentModules);
    }

    /**
     * 查询工艺配方信息列表
     * 
     * @param processRecipe 工艺配方信息
     * @return 工艺配方信息
     */
    @Override
    public List<ProcessRecipe> selectProcessRecipeList(ProcessRecipe processRecipe)
    {
        return processRecipeMapper.selectProcessRecipeList(processRecipe);
    }


    /**
     * 新增工艺配方信息
     *
     * @param ProcessRecipe 工艺配方信息
     * @return 结果
     */
    @Override
    public int insertProcessRecipe(ProcessRecipe processRecipe)
    {
        processRecipe.setCreateBy(SecurityUtils.getUsername());
        processRecipe.setCreateTime(DateUtils.getNowDate());
        return processRecipeMapper.insertProcessRecipe(processRecipe);
    }

    /**
     * 新增工艺配方信息
     * 
     * @param processVo 工艺配方信息,工艺流程信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertProcessRecipe(ProcessVo processVo)
    {
        ProcessRecipe processRecipe = processVo.getProcessRecipe();
        List<ProcessFlow> processFlows = processVo.getFlows();

        if(processRecipe.getRecipeId()==null){
            processRecipe.setCreateBy(SecurityUtils.getUsername());
            processRecipe.setCreateTime(DateUtils.getNowDate());
            processRecipeMapper.insertProcessRecipe(processRecipe);
        }else{
            processRecipe.setUpdateBy(SecurityUtils.getUsername());
            processRecipe.setUpdateTime(DateUtils.getNowDate());
            processRecipeMapper.updateProcessRecipe(processRecipe);
        }

        for (ProcessFlow processFlow : processFlows) {

            if(processFlow.getFlowId()==null){
                processFlow.setCreateBy(SecurityUtils.getUsername());
                processFlow.setCreateTime(DateUtils.getNowDate());
                processFlow.setRecipeId(processRecipe.getRecipeId());
                processFlow.setRecipeCode(processRecipe.getRecipeCode());
                processFlow.setRecipeName(processRecipe.getRecipeName());
                processFlowMapper.insertProcessFlow(processFlow);
            }else{
                processFlow.setUpdateBy(SecurityUtils.getUsername());
                processFlow.setUpdateTime(DateUtils.getNowDate());
                processFlow.setRecipeId(processRecipe.getRecipeId());
                processFlow.setRecipeCode(processRecipe.getRecipeCode());
                processFlow.setRecipeName(processRecipe.getRecipeName());
                processFlowMapper.updateProcessFlow(processFlow);
            }
        }
        return 1;
    }

    /**
     * 修改工艺配方信息
     * 
     * @param processRecipe 工艺配方信息
     * @return 结果
     */
    @Override
    public int updateProcessRecipe(ProcessRecipe processRecipe)
    {
        processRecipe.setUpdateBy(SecurityUtils.getUsername());
        processRecipe.setUpdateTime(DateUtils.getNowDate());
        return processRecipeMapper.updateProcessRecipe(processRecipe);
    }

    @Transactional
    @Override
    public int updateProcessRecipe(ProcessVo processVo) {
        return insertProcessRecipe(processVo);
    }

    /**
     * 批量删除工艺配方信息
     * 
     * @param recipeIds 需要删除的工艺配方信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteProcessRecipeByRecipeIds(Long[] recipeIds)
    {
        for (Long recipeId : recipeIds){
            List<ProcessFlow> processFlows = processFlowMapper.selectProcessFlowList(new ProcessFlow(recipeId));
            if(processFlows.size()>0){
                processFlowMapper.deleteProcessFlowByFlowIds(processFlows.stream()
                        .map(ProcessFlow::getFlowId)
                        .toArray(Long[]::new));
            }
        }

        return processRecipeMapper.deleteProcessRecipeByRecipeIds(recipeIds);
    }

    /**
     * 删除工艺配方信息信息
     * 
     * @param recipeId 工艺配方信息主键
     * @return 结果
     */
    @Override
    public int deleteProcessRecipeByRecipeId(Long recipeId)
    {
        return processRecipeMapper.deleteProcessRecipeByRecipeId(recipeId);
    }
}
