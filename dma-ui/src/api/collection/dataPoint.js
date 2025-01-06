import request from '@/utils/request'

// 查询数据点位配置列表
export function listDataPoint(query) {
  return request({
    url: '/system/dataPoint/list',
    method: 'get',
    params: query
  })
}

// 查询数据点位配置详细
export function getDataPoint(pointId) {
  return request({
    url: '/system/dataPoint/' + pointId,
    method: 'get'
  })
}

// 新增数据点位配置
export function addDataPoint(data) {
  return request({
    url: '/system/dataPoint',
    method: 'post',
    data: data
  })
}

// 修改数据点位配置
export function updateDataPoint(data) {
  return request({
    url: '/system/dataPoint',
    method: 'put',
    data: data
  })
}

// 修改数据点位配置
export function moveDataPoint(data) {
  return request({
    url: '/system/dataPoint/move',
    method: 'put',
    data: data
  })
}

// 删除数据点位配置
export function delDataPoint(pointId) {
  return request({
    url: '/system/dataPoint/' + pointId,
    method: 'delete'
  })
}

export function collectorTreeSelect(){
  return request({
    url: '/system/dataPoint/collectorTree',
    method: 'get'
  })
}
