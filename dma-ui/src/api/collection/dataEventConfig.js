import request from '@/utils/request'

// 查询事件配置列表
export function listDataEventConfig(query) {
  return request({
    url: '/system/dataEventConfig/list',
    method: 'get',
    params: query
  })
}

// 查询事件配置详细
export function getDataEventConfig(eventId) {
  return request({
    url: '/system/dataEventConfig/' + eventId,
    method: 'get'
  })
}

// 新增事件配置
export function addDataEventConfig(data) {
  return request({
    url: '/system/dataEventConfig',
    method: 'post',
    data: data
  })
}

// 修改事件配置
export function updateDataEventConfig(data) {
  return request({
    url: '/system/dataEventConfig',
    method: 'put',
    data: data
  })
}

// 删除事件配置
export function delDataEventConfig(eventId) {
  return request({
    url: '/system/dataEventConfig/' + eventId,
    method: 'delete'
  })
}

// 查询工厂模型下拉树结构
export function modelTreeSelect() {
  return request({
    url: '/system/factorymodel/factorymodelTree',
    method: 'get'
  })
}
