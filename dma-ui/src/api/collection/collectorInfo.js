import request from '@/utils/request'

// 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等列表
export function listCollectorInfo(query) {
  return request({
    url: '/collection/collectorInfo/list',
    method: 'get',
    params: query
  })
}

// 查询采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等详细
export function getCollectorInfo(collectorId) {
  return request({
    url: '/collection/collectorInfo/' + collectorId,
    method: 'get'
  })
}

// 新增采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
export function addCollectorInfo(data) {
  return request({
    url: '/collection/collectorInfo',
    method: 'post',
    data: data
  })
}

// 修改采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
export function updateCollectorInfo(data) {
  return request({
    url: '/collection/collectorInfo',
    method: 'put',
    data: data
  })
}

// 删除采集器基本信息：用于存储生产数据采集器的基础信息，如名称、类型、创建与更新时间等
export function delCollectorInfo(collectorId) {
  return request({
    url: '/collection/collectorInfo/' + collectorId,
    method: 'delete'
  })
}

// 查询工厂/设备模型下拉树结构
export function modelTreeSelect() {
  return request({
    url: '/system/equipmentInfo/factoryEquipmentInfoModelTree',
    method: 'get'
  })
}