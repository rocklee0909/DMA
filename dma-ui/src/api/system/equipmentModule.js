import request from '@/utils/request'

// 查询设备模块信息-子设备列表
export function listEquipmentModule(query) {
  return request({
    url: '/system/equipmentModule/list',
    method: 'get',
    params: query
  })
}

// 查询设备模块信息-子设备详细
export function getEquipmentModule(equipmentModuleId) {
  return request({
    url: '/system/equipmentModule/' + equipmentModuleId,
    method: 'get'
  })
}

// 新增设备模块信息-子设备
export function addEquipmentModule(data) {
  return request({
    url: '/system/equipmentModule',
    method: 'post',
    data: data
  })
}

// 修改设备模块信息-子设备
export function updateEquipmentModule(data) {
  return request({
    url: '/system/equipmentModule',
    method: 'put',
    data: data
  })
}

// 删除设备模块信息-子设备
export function delEquipmentModule(equipmentModuleId) {
  return request({
    url: '/system/equipmentModule/' + equipmentModuleId,
    method: 'delete'
  })
}
