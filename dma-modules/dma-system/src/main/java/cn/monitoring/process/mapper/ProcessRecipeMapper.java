package cn.monitoring.process.mapper;

import java.util.List;
import cn.monitoring.process.domain.ProcessRecipe;

/**
 * 工艺配方信息Mapper接口
 * 
 * @author liru
 * @date 2024-12-29
 */
public interface ProcessRecipeMapper 
{
    /**
     * 查询工艺配方信息
     * 
     * @param recipeId 工艺配方信息主键
     * @return 工艺配方信息
     */
    public ProcessRecipe selectProcessRecipeByRecipeId(Long recipeId);

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
     * 修改工艺配方信息
     * 
     * @param processRecipe 工艺配方信息
     * @return 结果
     */
    public int updateProcessRecipe(ProcessRecipe processRecipe);

    /**
     * 删除工艺配方信息
     * 
     * @param recipeId 工艺配方信息主键
     * @return 结果
     */
    public int deleteProcessRecipeByRecipeId(Long recipeId);

    /**
     * 批量删除工艺配方信息
     * 
     * @param recipeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessRecipeByRecipeIds(Long[] recipeIds);
}
