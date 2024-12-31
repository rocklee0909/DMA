import request from '@/utils/request'

// 查询数据类型配置列表
export function listDataType(query) {
  return request({
    url: '/collection/dataType/list',
    method: 'get',
    params: query
  })
}

// 查询数据类型配置详细
export function getDataType(typeId) {
  return request({
    url: '/collection/dataType/' + typeId,
    method: 'get'
  })
}

// 新增数据类型配置
export function addDataType(data) {
  return request({
    url: '/collection/dataType',
    method: 'post',
    data: data
  })
}

// 修改数据类型配置
export function updateDataType(data) {
  return request({
    url: '/collection/dataType',
    method: 'put',
    data: data
  })
}

// 删除数据类型配置
export function delDataType(typeId) {
  return request({
    url: '/collection/dataType/' + typeId,
    method: 'delete'
  })
}
