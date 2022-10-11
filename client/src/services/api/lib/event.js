import apiClient from '../apiClient'

const endpoint = '/api/events'

const getEvent = (params = { page, pageSize, sortBy, eventCategoryId }) => 
  apiClient.get(endpoint, { params })

const getEventById = (id) => 
  apiClient.get(`${endpoint}/${id}`)

// const getEventNonPage = (params = {eventCategoryId, date}) => apiClient.get('/events/all', params)

const postEvent = (data) => 
  apiClient.get(endpoint, data)

const patchEvent = ({id, data}) => 
  apiClient.patch(`${endpoint}/${id}`, data)

const deleteEvent = (id) => 
  apiClient.delete(`${endpoint}/${id}`)

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
