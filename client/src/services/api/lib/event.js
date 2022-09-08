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
      },
      headers: {
        auth: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
  },
  getById(id) {
    return apiClient.get(`/api/events/${id}`, {
      headers: {
        auth: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
  },
  getAll({ eventCategoryID, keyword }) {
    return apiClient.get('/events/all', {
      params: {
        eventCategoryID: eventCategoryID,
        keyword: keyword,
      },
      headers: {
        auth: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
  },
  post(data) {
    return apiClient.post(`/api/events/`, data, {
      headers: {
        auth: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
  },
  patch(id, data) {
    return apiClient.patch(`/api/events/${id}`, data, {
      headers: {
        auth: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
  },
  delete(id) {
    return apiClient.delete(`/api/events/${id}`, {
      headers: {
        auth: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
  },
}

export default apiEvent
