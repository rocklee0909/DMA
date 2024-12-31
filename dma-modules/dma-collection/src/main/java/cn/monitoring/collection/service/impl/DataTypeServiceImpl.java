package cn.monitoring.collection.service.impl;

import java.util.List;

import cn.monitoring.common.core.utils.StringUtils;
import cn.monitoring.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.collection.mapper.DataTypeMapper;
import cn.monitoring.collection.domain.DataType;
import cn.monitoring.collection.service.IDataTypeService;

/**
 * 数据类型配置Service业务层处理
 * 
 * @author liru
 * @date 2024-12-30
 */
@Service
public class DataTypeServiceImpl implements IDataTypeService 
{
    @Autowired
    private DataTypeMapper dataTypeMapper;

    /**
     * 查询数据类型配置
     * 
     * @param typeId 数据类型配置主键
     * @return 数据类型配置
     */
    @Override
    public DataType selectDataTypeByTypeId(Long typeId)
    {
        return dataTypeMapper.selectDataTypeByTypeId(typeId);
    }

    /**
     * 查询数据类型配置列表
     * 
     * @param dataType 数据类型配置
     * @return 数据类型配置
     */
    @Override
    public List<DataType> selectDataTypeList(DataType dataType)
    {
        return dataTypeMapper.selectDataTypeList(dataType);
    }

    /**
     * 新增数据类型配置
     * 
     * @param dataType 数据类型配置
     * @return 结果
     */
    @Override
    public int insertDataType(DataType dataType)
    {
        int result = dataTypeMapper.insertDataType(dataType);
        DataType parent = dataTypeMapper.selectDataTypeByTypeId(dataType.getParentId());
        if (parent == null) {
            dataType.setAncestors(dataType.getTypeId()+"");
        } else {
            dataType.setAncestors(parent.getAncestors() + "," + parent.getParentId());
        }

        dataType.setCreateBy(SecurityUtils.getUsername());
        dataType.setCreateTime(DateUtils.getNowDate());
        dataTypeMapper.updateDataType(dataType);
        return result;
    }

    /**
     * 修改数据类型配置
     * 
     * @param dataType 数据类型配置
     * @return 结果
     */
    @Override
    public int updateDataType(DataType dataType)
    {
        DataType newParentDataType = dataTypeMapper.selectDataTypeByTypeId(dataType.getParentId());
        DataType oldDataType = dataTypeMapper.selectDataTypeByTypeId(dataType.getTypeId());
        if (StringUtils.isNotNull(newParentDataType) && StringUtils.isNotNull(oldDataType))
        {
            String newAncestors = newParentDataType.getAncestors() + "," + oldDataType.getTypeId();
            String oldAncestors = oldDataType.getAncestors();
            dataType.setAncestors(newAncestors);
            updateDataTypeChildren(dataType.getTypeId(), newAncestors, oldAncestors);
        }else if (StringUtils.isNull(newParentDataType) && StringUtils.isNotNull(oldDataType)){
            String newAncestors =  oldDataType.getTypeId()+"";
            String oldAncestors = oldDataType.getAncestors();
            dataType.setAncestors(newAncestors);
            updateDataTypeChildren(dataType.getTypeId(), newAncestors, oldAncestors);
        }

        dataType.setUpdateBy(SecurityUtils.getUsername());
        dataType.setUpdateTime(DateUtils.getNowDate());
        return dataTypeMapper.updateDataType(dataType);
    }

    private void updateDataTypeChildren(Long typeId, String newAncestors, String oldAncestors) {
        List<DataType> children = dataTypeMapper.selectChildrenDataTypeById(typeId);
        for (DataType child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            dataTypeMapper.updateDataTypeChildren(children);
        }
    }

    /**
     * 批量删除数据类型配置
     * 
     * @param typeIds 需要删除的数据类型配置主键
     * @return 结果
     */
    @Override
    public int deleteDataTypeByTypeIds(Long[] typeIds)
    {
        return dataTypeMapper.deleteDataTypeByTypeIds(typeIds);
    }

    /**
     * 删除数据类型配置信息
     * 
     * @param typeId 数据类型配置主键
     * @return 结果
     */
    @Override
    public int deleteDataTypeByTypeId(Long typeId)
    {
        return dataTypeMapper.deleteDataTypeByTypeId(typeId);
    }
}
