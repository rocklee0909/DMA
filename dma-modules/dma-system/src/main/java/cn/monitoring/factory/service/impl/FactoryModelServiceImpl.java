package cn.monitoring.factory.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import cn.monitoring.common.core.constant.UserConstants;
import cn.monitoring.common.core.exception.ServiceException;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.common.core.utils.SpringUtils;
import cn.monitoring.common.core.utils.StringUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import cn.monitoring.system.api.domain.SysDept;
import cn.monitoring.system.domain.vo.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.factory.mapper.FactoryModelMapper;
import cn.monitoring.factory.domain.FactoryModel;
import cn.monitoring.factory.service.IFactoryModelService;

/**
 * 工厂建模Service业务层处理
 * 
 * @author liru
 * @date 2024-12-27
 */
@Service
public class FactoryModelServiceImpl implements IFactoryModelService 
{
    @Autowired
    private FactoryModelMapper factoryModelMapper;

    /**
     * 查询工厂建模
     * 
     * @param modelId 工厂建模主键
     * @return 工厂建模
     */
    @Override
    public FactoryModel selectFactoryModelByModelId(Long modelId)
    {
        return factoryModelMapper.selectFactoryModelByModelId(modelId);
    }

    /**
     * 查询工厂建模列表
     * 
     * @param factoryModel 工厂建模
     * @return 工厂建模
     */
    @Override
    public List<FactoryModel> selectFactoryModelList(FactoryModel factoryModel)
    {
        return factoryModelMapper.selectFactoryModelList(factoryModel);
    }

    /**
     * 新增工厂建模
     * 
     * @param factoryModel 工厂建模
     * @return 结果
     */
    @Override
    public int insertFactoryModel(FactoryModel factoryModel)
    {
        factoryModel.setCreateBy(SecurityUtils.getUsername());
        factoryModel.setCreateTime(DateUtils.getNowDate());

        String ancestors = "0";

        if(factoryModel.getParentId()==null || factoryModel.getParentId()==0){
            ancestors = "0";
            factoryModel.setAncestors(ancestors);
        }else{
            FactoryModel info = factoryModelMapper.selectFactoryModelByModelId(factoryModel.getParentId());
            // 如果父节点不为正常状态,则不允许新增子节点
            if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
            {
                throw new ServiceException("父工厂模型停用，不允许新增");
            }
            ancestors = info.getAncestors();
            factoryModel.setAncestors(ancestors + "," + factoryModel.getParentId());
        }

        return factoryModelMapper.insertFactoryModel(factoryModel);
    }

    /**
     * 修改工厂建模
     * 
     * @param factoryModel 工厂建模
     * @return 结果
     */
    @Override
    public int updateFactoryModel(FactoryModel factoryModel)
    {
        factoryModel.setUpdateBy(SecurityUtils.getUsername());
        factoryModel.setUpdateTime(DateUtils.getNowDate());
        return factoryModelMapper.updateFactoryModel(factoryModel);
    }

    /**
     * 批量删除工厂建模
     * 
     * @param modelIds 需要删除的工厂建模主键
     * @return 结果
     */
    @Override
    public int deleteFactoryModelByModelIds(Long[] modelIds)
    {
        return factoryModelMapper.deleteFactoryModelByModelIds(modelIds);
    }

    /**
     * 删除工厂建模信息
     * 
     * @param modelId 工厂建模主键
     * @return 结果
     */
    @Override
    public int deleteFactoryModelByModelId(Long modelId)
    {
        return factoryModelMapper.deleteFactoryModelByModelId(modelId);
    }

    /**
     * 判断是否有子节点
     * @param modelId
     * @return
     */
    @Override
    public boolean hasChildByModelId(Long modelId) {
        int result = factoryModelMapper.hasChildByModelId(modelId);
        return result > 0;
    }

    @Override
    public int selectNormalChildrenModelById(Long modelId) {
        return factoryModelMapper.selectNormalChildrenModelById(modelId);
    }

    /**
     * 查询工厂模型树结构信息
     *
     * @param factoryModel 工厂模型信息
     * @return 工厂模型树信息集合
     */
    @Override
    public List<TreeSelect> selectModelTreeList(FactoryModel factoryModel)
    {
        List<FactoryModel> factoryModels = selectFactoryModelList(factoryModel);
        return buildModelTreeSelect(factoryModels);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param factoryModels 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildModelTreeSelect(List<FactoryModel> factoryModels)
    {
        List<FactoryModel> modelTrees = buildModelTree(factoryModels);
        return modelTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param factoryModels 工厂模型列表
     * @return 树结构列表
     */
    private List<FactoryModel> buildModelTree(List<FactoryModel> factoryModels) {
        List<FactoryModel> returnList = new ArrayList<FactoryModel>();
        List<Long> tempList = factoryModels.stream().map(FactoryModel::getModelId).collect(Collectors.toList());
        for (FactoryModel factoryModel : factoryModels)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(factoryModel.getParentId()))
            {
                recursionFn(factoryModels, factoryModel);
                returnList.add(factoryModel);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = factoryModels;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<FactoryModel> list, FactoryModel t) {
        // 得到子节点列表
        List<FactoryModel> childList = getChildList(list, t);
        t.setChildren(childList);
        for (FactoryModel tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<FactoryModel> getChildList(List<FactoryModel> list, FactoryModel t)
    {
        List<FactoryModel> tlist = new ArrayList<FactoryModel>();
        Iterator<FactoryModel> it = list.iterator();
        while (it.hasNext())
        {
            FactoryModel n = (FactoryModel) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getModelId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<FactoryModel> list, FactoryModel t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
