import apiClient from '../apiClient'

const apiEventCategory = {
    get(){
        return apiClient.get('/api/eventcategories',{
            headers: {
              auth: `Bearer ${localStorage.getItem('jwt')}`,
            },
          })
    }
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
