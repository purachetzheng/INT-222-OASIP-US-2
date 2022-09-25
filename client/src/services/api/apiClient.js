import axios from 'axios'
import { useRouter } from 'vue-router'
import router from '../../router'
// const router = useRouter()
const defaultOptions = {
  baseURL: import.meta.env.VITE_BASE_URL,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
}

const apiClient = axios.create(defaultOptions)
apiClient.interceptors.request.use(function (config) {
  const token = localStorage.getItem('jwt')
  // config.headers.Authorization =  token ? `Bearer ${token}` : '';
  if (isAuthRequired(config.url))
    config.headers.auth = token ? `Bearer ${token}` : ''
  return config
})

const isAuthRequired = (url) => {
  if (url === '/api/users/login') return false
  if (url === '/api/users/refresh') return false
  return true
}

apiClient.interceptors.response.use(response => response, async (error) => {
  const { config, response, message } = error;
  console.log(config);
  console.log(config.retry);
  console.log(response);
  if (!config || config.retry ) {
    console.log('err: 1');
    return Promise.reject(error);
  }
  if(config.url == '/api/users/refresh') {
    console.log(config.url == '/api/users/refresh');
    return Promise.reject(error);
  }
  
  // config && response && response.status === 401
  if (response && response.status === 401) {
    await updateToken()
    config.retry = 1;
    console.log('retry');
    return apiClient.request(config);
  }

  return Promise.reject(error)
})

const updateToken = async () => {
  const refreshToken = localStorage.getItem('refreshToken')
  try {
    const {data} = await apiClient.get(`/api/users/refresh`, {
      headers: { auth: `Bearer ${refreshToken}` },
    })
    console.log(data);
    localStorage.setItem('jwt', data.token);
    console.log('suc: refresh');
  }
  catch(error){
    console.log('ref err:');
    console.log(error);
    const { data, status } = error.response
    console.log(status);
    if(status === 401) {
    //   console.log('refresh exp');
      return router.push({ name: 'Authentication'})
    }
    // router
    console.log(error);
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
