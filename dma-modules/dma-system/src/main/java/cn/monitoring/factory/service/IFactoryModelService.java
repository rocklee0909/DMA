package cn.monitoring.factory.service;

import java.util.List;
import cn.monitoring.factory.domain.FactoryModel;
import cn.monitoring.system.domain.vo.TreeSelect;

/**
 * 工厂建模Service接口
 * 
 * @author liru
 * @date 2024-12-27
 */
public interface IFactoryModelService 
{
    /**
     * 查询工厂建模
     * 
     * @param modelId 工厂建模主键
     * @return 工厂建模
     */
    public FactoryModel selectFactoryModelByModelId(Long modelId);

    /**
     * 查询工厂建模列表
     * 
     * @param factoryModel 工厂建模
     * @return 工厂建模集合
     */
    public List<FactoryModel> selectFactoryModelList(FactoryModel factoryModel);

    /**
     * 新增工厂建模
     * 
     * @param factoryModel 工厂建模
     * @return 结果
     */
    public int insertFactoryModel(FactoryModel factoryModel);

    /**
     * 修改工厂建模
     * 
     * @param factoryModel 工厂建模
     * @return 结果
     */
    public int updateFactoryModel(FactoryModel factoryModel);

    /**
     * 批量删除工厂建模
     * 
     * @param modelIds 需要删除的工厂建模主键集合
     * @return 结果
     */
    public int deleteFactoryModelByModelIds(Long[] modelIds);

    /**
     * 删除工厂建模信息
     * 
     * @param modelId 工厂建模主键
     * @return 结果
     */
    public int deleteFactoryModelByModelId(Long modelId);

    /**
     * 判断是否有子节点
     * @param modelId
     * @return
     */
    boolean hasChildByModelId(Long modelId);

    /**
     * 查询正常状态子节点
     * @param modelId
     * @return
     */
    int selectNormalChildrenModelById(Long modelId);

    /**
     * 查询工厂模型树结构信息
     * @param factoryModel
     * @return
     */
    List<TreeSelect> selectModelTreeList(FactoryModel factoryModel);

    /**
     * 构建工厂模型树结构
     * @param factoryModels
     * @return
     */
    List<TreeSelect> buildModelTreeSelect(List<FactoryModel> factoryModels);
}
