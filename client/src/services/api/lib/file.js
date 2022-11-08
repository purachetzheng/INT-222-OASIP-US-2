import apiClient from '../apiClient'

const endpoint = '/api/file/'

export const getFile = (fileName,params = { nameOnly: true }) => 
  apiClient.get(endpoint+fileName, { params })
