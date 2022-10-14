import apiClient from '../apiClient'

const apiAuth = {
    get(){
        return apiClient.get(`/api/auth/profile`)
    },
    login(data){
        return apiClient.post(`/api/auth/login`, data)
    },
    logout(){
        return apiClient.post(`/api/auth/logout`)
    },
}

export default apiAuth
