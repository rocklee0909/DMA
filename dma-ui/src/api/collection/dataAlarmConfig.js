import request from '@/utils/request'

// 查询告警配置列表
export function listDataAlarmConfig(query) {
  return request({
    url: '/system/dataAlarmConfig/list',
    method: 'get',
    params: query
  })
}

// 查询告警配置详细
export function getDataAlarmConfig(alertId) {
  return request({
    url: '/system/dataAlarmConfig/' + alertId,
    method: 'get'
  })
}

// 新增告警配置
export function addDataAlarmConfig(data) {
  return request({
    url: '/system/dataAlarmConfig',
    method: 'post',
    data: data
  })
}

// 修改告警配置
export function updateDataAlarmConfig(data) {
  return request({
    url: '/system/dataAlarmConfig',
    method: 'put',
    data: data
  })
}

// 删除告警配置
export function delDataAlarmConfig(alertId) {
  return request({
    url: '/system/dataAlarmConfig/' + alertId,
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
