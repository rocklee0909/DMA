import request from '@/utils/request'

// 查询数据类型点位关联列表
export function listDataTypePoint(query) {
  return request({
    url: '/collection/dataTypePoint/list',
    method: 'get',
    params: query
  })
}

// 查询数据类型点位关联详细
export function getDataTypePoint(typeId) {
  return request({
    url: '/collection/dataTypePoint/' + typeId,
    method: 'get'
  })
}

// 新增数据类型点位关联
export function addDataTypePoint(data) {
  return request({
    url: '/collection/dataTypePoint',
    method: 'post',
    data: data
  })
}

// 修改数据类型点位关联
export function updateDataTypePoint(data) {
  return request({
    url: '/collection/dataTypePoint',
    method: 'put',
    data: data
  })
}

// 删除数据类型点位关联
export function delDataTypePoint(typeId) {
  return request({
    url: '/collection/dataTypePoint/' + typeId,
    method: 'delete'
  })
}

// 查询采集器树形菜单数据
export function collectorTreeSelect(){
  return request({
    url: '/collection/dataTypePoint/collectorTree',
    method: 'get'
  })
}


// 清除数据点关联类型
export function clearDataTypePoint(pointId) {
  return request({
    url: '/collection/dataTypePoint/clear/' + pointId,
    method: 'delete'
  })
}
