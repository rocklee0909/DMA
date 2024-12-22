import request from '@/utils/request'

// 查询数据点位配置列表
export function listDataPoint(query) {
  return request({
    url: '/collection/dataPoint/list',
    method: 'get',
    params: query
  })
}

// 查询数据点位配置详细
export function getDataPoint(pointId) {
  return request({
    url: '/collection/dataPoint/' + pointId,
    method: 'get'
  })
}

// 新增数据点位配置
export function addDataPoint(data) {
  return request({
    url: '/collection/dataPoint',
    method: 'post',
    data: data
  })
}

// 修改数据点位配置
export function updateDataPoint(data) {
  return request({
    url: '/collection/dataPoint',
    method: 'put',
    data: data
  })
}

// 删除数据点位配置
export function delDataPoint(pointId) {
  return request({
    url: '/collection/dataPoint/' + pointId,
    method: 'delete'
  })
}
