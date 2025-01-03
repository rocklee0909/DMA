import request from '@/utils/request'

// 查询预处理规则配置列表
export function listDataPretreatmentConfig(query) {
  return request({
    url: '/collection/dataPretreatmentConfig/list',
    method: 'get',
    params: query
  })
}

// 查询预处理规则配置详细
export function getDataPretreatmentConfig(pretreatmentId) {
  return request({
    url: '/collection/dataPretreatmentConfig/' + pretreatmentId,
    method: 'get'
  })
}

// 新增预处理规则配置
export function addDataPretreatmentConfig(data) {
  return request({
    url: '/collection/dataPretreatmentConfig',
    method: 'post',
    data: data
  })
}

// 修改预处理规则配置
export function updateDataPretreatmentConfig(data) {
  return request({
    url: '/collection/dataPretreatmentConfig',
    method: 'put',
    data: data
  })
}

// 删除预处理规则配置
export function delDataPretreatmentConfig(pretreatmentId) {
  return request({
    url: '/collection/dataPretreatmentConfig/' + pretreatmentId,
    method: 'delete'
  })
}
