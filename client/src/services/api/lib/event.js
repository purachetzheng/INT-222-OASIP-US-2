import apiClient from '../apiClient'

const apiEvent = {
  get({ page, pageSize, sortBy, filter, eventCategoryID, keyword } = {}) {
    return apiClient.get('/api/events', {
      params: {
        page: page,
        pageSize: pageSize,
        sortBy: sortBy,
        filter: filter,
        eventCategoryID: eventCategoryID,
        keyword: keyword,
      }
    })
  },
  getById(id) {
    return apiClient.get(`/api/events/${id}`)
  },
  getAll({ eventCategoryID, keyword }) {
    return apiClient.get('/events/all', {
      params: {
        eventCategoryID: eventCategoryID,
        keyword: keyword,
      }
    })
  },
  post(data) {
    return apiClient.post(`/api/events/`, data)
  },
  patch(id, data) {
    return apiClient.patch(`/api/events/${id}`, data)
  },
  delete(id) {
    return apiClient.delete(`/api/events/${id}`)
  },
}

export default apiEvent
