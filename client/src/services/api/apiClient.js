import axios from 'axios'
import router from '../../router'

const defaultOptions = {
  baseURL: import.meta.env.VITE_BASE_URL,
  withCredentials: true,
  headers: {
    Accept: '*/*',
    'Content-Type': 'application/json',
    withCredentials: true,
  },
}

// const setAuthToken = token => {
//   if (token) {
//       axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
//   }
//   else
//       delete axios.defaults.headers.common["Authorization"];
// }

const apiClient = axios.create(defaultOptions)
apiClient.interceptors.request.use(async (config) => {
  const token = localStorage.getItem('accessToken')
  // const refreshToken = localStorage.getItem('refreshToken')

  // console.log(config.method);
  // console.log(config.url);
  // console.log(config.headers);
  // config.headers.Authorization =  token ? `Bearer ${token}` : '';
  if (isAuthRequired(config)) {
    // console.log(config.url + ' is auth');
    config.headers.auth = token ? `Bearer ${token}` : ''
  }
  return config
})
const noAuthRequired = [
  { url: '/api/auth/refresh' },
  { url: '/api/auth/login' },
  { url: '/api/users', method: ['post'] },
]
const isAuthRequired = ({ url, method } = {}) => {
  if (
    noAuthRequired.some(
      (noAuthItem) =>
        noAuthItem.url === url &&
        (!noAuthItem.method ||
          noAuthItem.method.some((noAuthMethod) => noAuthMethod === method))
    )
  )
    return false
  return true
}

apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const { config, response, message } = error
    if (!config || config.retry) {
      return Promise.reject(error)
    }
    if (config.url == '/api/users/refresh') {
      console.log(config.url == '/api/users/refresh')
      return Promise.reject(error)
    }

    // config && response && response.status === 401
    if (response && response.status === 401) {
      await refreshToken()
      config.retry = 1
      console.log('retry')
      return apiClient.request(config)
    }

    return Promise.reject(error)
  }
)

const refreshToken = async () => {
  try {
    const { data } = await apiClient.get(`/api/auth/refresh`, { retry: 1 })
    localStorage.setItem('accessToken', data.accessToken)
  } catch (error) {
    const res = error.response
    const { status } = res
    if (status === 401) {
      localStorage.removeItem('accessToken')
      alert('Session Expired\nPlease sign in again.')
      return router.push({ name: 'Authentication' })
    }
    return Promise.reject(error)
  }
}
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
