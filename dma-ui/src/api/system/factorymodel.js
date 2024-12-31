import request from '@/utils/request'

// 查询工厂模型列表
export function listFactorymodel(query) {
  return request({
    url: '/system/factorymodel/list',
    method: 'get',
    params: query
  })
}

// 查询工厂模型列表（排除节点）
export function listFactorymodelExcludeChild(deptId) {
  return request({
    url: '/system/factorymodel/list/exclude/' + deptId,
    method: 'get'
  })
}

// 查询工厂模型详细
export function getFactorymodel(deptId) {
  return request({
    url: '/system/factorymodel/' + deptId,
    method: 'get'
  })
}

// 新增工厂模型
export function addFactorymodel(data) {
  return request({
    url: '/system/factorymodel',
    method: 'post',
    data: data
  })
}

// 修改工厂模型
export function updateFactorymodel(data) {
  return request({
    url: '/system/factorymodel',
    method: 'put',
    data: data
  })
}

// 删除工厂模型
export function delFactorymodel(deptId) {
  return request({
    url: '/system/factorymodel/' + deptId,
    method: 'delete'
  })
}
