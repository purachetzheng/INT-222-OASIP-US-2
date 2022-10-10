import apiClient from '../apiClient'

const endpoint = '/api/eventcategories'

export const apiGetEventCategory = (
  params = { page: null, pageSize: null, sortBy: null }
) => apiClient.get(endpoint, { params })

export const apiPatchEventCategory = ({ id, data }) =>
  apiClient.put(`${endpoint}/${id}`, data)

const apiEventCategory = {
  get: () => apiClient.get('/api/eventcategories'),

  //   get({page, pageSize, sortBy, filter, eventCategoryID, keyword} = {}) {
  //     return apiClient.get('/events', { params: {
  //         page: page,
  //         pageSize: pageSize,
  //         sortBy: sortBy,
  //         filter: filter,
  //         eventCategoryID: eventCategoryID,
  //         keyword: keyword
  //     } })
  //   },
  //   getById(id) {
  //     return apiClient.get(`/events/${id}`)
  //   },
  //   getAll({eventCategoryID,keyword}){
  //     return apiClient.get('/events/all', { params: {
  //       eventCategoryID: eventCategoryID,
  //       keyword: keyword
  //   } })
  //   },
  //   patch(id, data){
  //     return apiClient.patch(`/events/${id}`, data)
  //   },
  //   delete(id){
  //     return apiClient.delete(`/events/${id}`)
  //   }
}

export default apiEventCategory
