import apiClient from '../apiClient'

const apiEvent = {
  get({ page, pageSize, sortBy, dateStatus, eventCategoryId, date } = {}) {
    const params = {
        page: page,
        pageSize: pageSize,
        sortBy: sortBy,
        dateStatus: dateStatus,
        eventCategoryId: eventCategoryId,
        date: date,
    }
    return apiClient.get('/api/events', {
      params: params
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
