import apiClient from '../../apiClient'

const apiUser = {
    get(){
        return apiClient.get(`/api/users`,{
            headers: {
                auth: `Bearer ${localStorage.getItem('jwt')}`,
            },
          })
    },
    post(data){
        return apiClient.post(`/api/users`, data,{
            headers: {
                auth: `Bearer ${localStorage.getItem('jwt')}`,
            },
          })
    },
    delete(id){
        return apiClient.delete(`/api/users/${id}`,{
            headers: {
                auth: `Bearer ${localStorage.getItem('jwt')}`,
            },
          })
    },
    patch(id, data){
        return apiClient.patch(`/api/users/${id}`, data,{
            headers: {
                auth: `Bearer ${localStorage.getItem('jwt')}`,
            },
          })
    },
    signIn(data){
        return apiClient.post(`/api/users/login`, data)
    }
}

export default apiUser
