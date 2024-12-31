package cn.monitoring.process.service;

import java.util.List;
import cn.monitoring.process.domain.ProcessRecipe;
import cn.monitoring.process.domain.vo.ProcessVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工艺配方信息Service接口
 * 
 * @author liru
 * @date 2024-12-29
 */
public interface IProcessRecipeService 
{
    /**
     * 查询工艺配方信息
     *
     * @param recipeId 工艺配方信息主键
     * @return 工艺配方信息
     */
    public ProcessVo selectProcessRecipeByRecipeId(Long recipeId);

    /**
     * 查询工艺配方信息列表
     * 
     * @param processRecipe 工艺配方信息
     * @return 工艺配方信息集合
     */
    public List<ProcessRecipe> selectProcessRecipeList(ProcessRecipe processRecipe);

    /**
     * 新增工艺配方信息
     *
     * @param processRecipe 工艺配方信息
     * @return 结果
     */
    public int insertProcessRecipe(ProcessRecipe processRecipe);

    /**
     * 新增工艺配方信息
     * 
     * @param processVo 工艺配方信息,工艺流程信息
     * @return 结果
     */
    public int insertProcessRecipe(ProcessVo processVo);

    /**
     * 修改工艺配方信息
     * 
     * @param processRecipe 工艺配方信息
     * @return 结果
     */
    public int updateProcessRecipe(ProcessRecipe processRecipe);

    @Transactional
    int updateProcessRecipe(ProcessVo processVo);

    /**
     * 批量删除工艺配方信息
     * 
     * @param recipeIds 需要删除的工艺配方信息主键集合
     * @return 结果
     */
    public int deleteProcessRecipeByRecipeIds(Long[] recipeIds);

    /**
     * 删除工艺配方信息信息
     * 
     * @param recipeId 工艺配方信息主键
     * @return 结果
     */
    public int deleteProcessRecipeByRecipeId(Long recipeId);
}
