import apiClient from '../apiClient'

const apiUser = {
    get(){
        return apiClient.get(`/api/users`,)
    },
    getById(id){
        return apiClient.get(`/api/users/${id}`)
    },
    post(data){
        return apiClient.post(`/api/users`, data,)
    },
    delete(id){
        return apiClient.delete(`/api/users/${id}`,)
    },
    patch(id, data){
        return apiClient.patch(`/api/users/${id}`, data,)
    },
    signIn(data){
        return apiClient.post(`/api/auth/login`, data)
    }
}

export default apiUser
