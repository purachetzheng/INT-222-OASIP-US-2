import apiClient from '../../apiClient'

const apiEvent = {
  get({page, pageSize, sortBy, filter, eventCategoryID, keyword} = {}) {
    return apiClient.get('/events', { params: {
        page: page,
        pageSize: pageSize,
        sortBy: sortBy,
        filter: filter,
        eventCategoryID: eventCategoryID,
        keyword: keyword
    } })
  },
  getById(id) {
    return apiClient.get(`/events/${id}`)
  },
}

export default apiEvent
