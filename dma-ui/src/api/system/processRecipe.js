import request from '@/utils/request'

// 查询工艺配方信息列表
export function listProcessRecipe(query) {
  return request({
    url: '/system/processRecipe/list',
    method: 'get',
    params: query
  })
}

// 查询工艺配方信息详细
export function getProcessRecipe(recipeId) {
  return request({
    url: '/system/processRecipe/' + recipeId,
    method: 'get'
  })
}

// 新增工艺配方信息
export function addProcessRecipe(data) {
  return request({
    url: '/system/processRecipe',
    method: 'post',
    data: data
  })
}

// 修改工艺配方信息
export function updateProcessRecipe(data) {
  return request({
    url: '/system/processRecipe',
    method: 'put',
    data: data
  })
}

// 删除工艺配方信息
export function delProcessRecipe(recipeId) {
  return request({
    url: '/system/processRecipe/' + recipeId,
    method: 'delete'
  })
}

// 查询工厂/设备模型下拉树结构
export function modelTreeSelect() {
  return request({
    url: '/system/processRecipe/factoryModelTree',
    method: 'get'
  })
}

