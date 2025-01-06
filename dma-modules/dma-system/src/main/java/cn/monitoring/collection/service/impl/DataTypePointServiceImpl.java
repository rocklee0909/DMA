package cn.monitoring.collection.service.impl;

import java.util.List;

import cn.monitoring.collection.domain.DataType;
import cn.monitoring.collection.domain.vo.DataTypePointVo;
import cn.monitoring.collection.mapper.DataTypeMapper;
import cn.monitoring.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.monitoring.common.core.utils.DateUtils;
import cn.monitoring.collection.mapper.DataTypePointMapper;
import cn.monitoring.collection.domain.DataTypePoint;
import cn.monitoring.collection.service.IDataTypePointService;

/**
 * 数据类型点位关联Service业务层处理
 * 
 * @author liru
 * @date 2024-12-31
 */
@Slf4j
@Service
public class DataTypePointServiceImpl implements IDataTypePointService 
{
    @Autowired
    private DataTypePointMapper dataTypePointMapper;

    @Autowired
    private DataTypeMapper dataTypeMapper;

    /**
     * 查询数据类型点位关联
     * 
     * @param typeId 数据类型点位关联主键
     * @return 数据类型点位关联
     */
    @Override
    public DataTypePoint selectDataTypePointByTypeId(Long typeId)
    {
        return dataTypePointMapper.selectDataTypePointByTypeId(typeId);
    }

    /**
     * 查询数据类型点位关联列表
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 数据类型点位关联
     */
    @Override
    public List<DataTypePoint> selectDataTypePointList(DataTypePoint dataTypePoint)
    {
        return dataTypePointMapper.selectDataTypePointList(dataTypePoint);
    }

    /**
     * 新增数据类型点位关联
     * 
     * @param dataTypePointVo 数据类型点位关联Vo
     * @return 结果
     */
    @Override
    public int insertDataTypePoint(DataTypePointVo dataTypePointVo)
    {
        Long[] pointIds = dataTypePointVo.getPointIds();
        Long[] typeIds = dataTypePointVo.getTypeIds();

        int result = 0;
        for (Long typeId : typeIds) {
            for (Long pointId : pointIds) {
                DataTypePoint dataTypePoint = new DataTypePoint(typeId,pointId);
                try {
                    result += dataTypePointMapper.insertDataTypePoint(dataTypePoint);

                    // 关联成功后，更新数据类型的数量
                    DataType dataType = dataTypeMapper.selectDataTypeByTypeId(typeId);
                    dataType.setDataNum(dataTypePointMapper.countByTypeId(typeId));
                    dataTypeMapper.updateDataType(dataType);
                }catch (Exception e) {
                    log.error("重复关联数据点类型。",e);
                }
            }
        }
        return result;
    }

    /**
     * 修改数据类型点位关联
     * 
     * @param dataTypePoint 数据类型点位关联
     * @return 结果
     */
    @Override
    public int updateDataTypePoint(DataTypePoint dataTypePoint)
    {
        dataTypePoint.setUpdateBy(SecurityUtils.getUsername());
        dataTypePoint.setUpdateTime(DateUtils.getNowDate());
        return dataTypePointMapper.updateDataTypePoint(dataTypePoint);
    }

    /**
     * 批量删除数据类型点位关联
     * 
     * @param typeIds 需要删除的数据类型点位关联主键
     * @return 结果
     */
    @Override
    public int deleteDataTypePointByTypeIds(Long[] typeIds)
    {
        return dataTypePointMapper.deleteDataTypePointByTypeIds(typeIds);
    }

    /**
     * 删除数据类型点位关联信息
     * 
     * @param typeId 数据类型点位关联主键
     * @return 结果
     */
    @Override
    public int deleteDataTypePointByTypeId(Long typeId)
    {
        return dataTypePointMapper.deleteDataTypePointByTypeId(typeId);
    }

    /**
     * 删除数据点位关联类型信息
     * @param pointIds
     * @return
     */
    @Override
    public int deleteDataTypePointByPointIds(Long[] pointIds) {
        return dataTypePointMapper.deleteDataTypePointByPointIds(pointIds);
    }

}
