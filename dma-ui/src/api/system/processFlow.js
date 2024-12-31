import request from '@/utils/request'

// 查询工艺流程信息列表
export function listProcessFlow(query) {
  return request({
    url: '/system/processFlow/list',
    method: 'get',
    params: query
  })
}

// 查询工艺流程信息详细
export function getProcessFlow(flowId) {
  return request({
    url: '/system/processFlow/' + flowId,
    method: 'get'
  })
}

// 新增工艺流程信息
export function addProcessFlow(data) {
  return request({
    url: '/system/processFlow',
    method: 'post',
    data: data
  })
}

// 修改工艺流程信息
export function updateProcessFlow(data) {
  return request({
    url: '/system/processFlow',
    method: 'put',
    data: data
  })
}

// 删除工艺流程信息
export function delProcessFlow(flowId) {
  return request({
    url: '/system/processFlow/' + flowId,
    method: 'delete'
  })
}
