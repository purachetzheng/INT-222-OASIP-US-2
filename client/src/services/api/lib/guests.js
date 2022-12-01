import apiClient from '../apiClient'

const endpoint = '/api/guests'

export const postGuestsEvent = (data) => 
  apiClient.post(endpoint, data, {requiresAuth: false})
