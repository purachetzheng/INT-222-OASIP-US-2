import axios from 'axios'

const defaultOptions = {
  baseURL: import.meta.env.VITE_BASE_URL,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
}

const apiClient = axios.create(defaultOptions)
// apiClient.interceptors.request.use(function (config) {
//   const token = localStorage.getItem('jwt');
//   config.headers.Authorization =  token ? `Bearer ${token}` : '';
//   return config;
// });
// apiClient.interceptors.response.use(
//   function (response) {
//     return response
//   },
//   function (error) {
//     let res = error.response
//     if (res.status == 404) {
//     }
//     console.error('Looks like there was a problem. Status Code: ' + res.status)
//     return Promise.reject(error)
//   }
// )
export default apiClient