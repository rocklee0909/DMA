import request from '@/utils/request'

// 查询设备基本信息列表
export function listEquipmentInfo(query) {
  return request({
    url: '/system/equipmentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询设备基本信息详细
export function getEquipmentInfo(equipmentInfoId) {
  return request({
    url: '/system/equipmentInfo/' + equipmentInfoId,
    method: 'get'
  })
}

// 新增设备基本信息
export function addEquipmentInfo(data) {
  return request({
    url: '/system/equipmentInfo',
    method: 'post',
    data: data
  })
}

// 修改设备基本信息
export function updateEquipmentInfo(data) {
  return request({
    url: '/system/equipmentInfo',
    method: 'put',
    data: data
  })
}

// 删除设备基本信息
export function delEquipmentInfo(equipmentInfoId) {
  return request({
    url: '/system/equipmentInfo/' + equipmentInfoId,
    method: 'delete'
  })
}

// 查询工厂模型下拉树结构
export function modelTreeSelect() {
  return request({
    url: '/system/equipmentInfo/factoryModelTree',
    method: 'get'
  })
}
