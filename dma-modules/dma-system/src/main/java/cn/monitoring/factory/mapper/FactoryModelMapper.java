package cn.monitoring.factory.mapper;

import java.util.List;
import cn.monitoring.factory.domain.FactoryModel;

/**
 * 工厂建模Mapper接口
 * 
 * @author liru
 * @date 2024-12-27
 */
public interface FactoryModelMapper 
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
     * 删除工厂建模
     * 
     * @param modelId 工厂建模主键
     * @return 结果
     */
    public int deleteFactoryModelByModelId(Long modelId);

    /**
     * 批量删除工厂建模
     * 
     * @param modelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFactoryModelByModelIds(Long[] modelIds);

    /**
     * 判断是否有子节点
     * @param modelId
     * @return
     */
    public int hasChildByModelId(Long modelId);

    /**
     *
     * @param modelId
     * @return
     */
    public int selectNormalChildrenModelById(Long modelId);
}
