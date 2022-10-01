import axios from 'axios'
import router from '../../router'

const defaultOptions = {
  baseURL: import.meta.env.VITE_BASE_URL,
  withCredentials: true,
  headers: {
    Accept: '*/*',
    'Content-Type': 'application/json',
    withCredentials: true
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
  if (isAuthRequired(config.url, config.method)){
    // console.log(config.url + ' is auth');
    config.headers.auth = token ? `Bearer ${token}` : ''
  }
  return config
})

const isAuthRequired = (url, method) => {
  if (url === '/api/users/login') return false
  if (url === '/api/users/refresh') return false
  if (url === '/api/auth/login') return false
  if (url === '/api/users' && method === 'post') console.log('ok it is');
  if (url === '/api/users' && method === 'post') return false
  return true
}

apiClient.interceptors.response.use(response => response, async (error) => {
  const { config, response, message } = error;
  // console.log(config);
  // console.log(config.retry);
  // console.log(response);
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
    await refreshToken()
    config.retry = 1;
    console.log('retry');
    return apiClient.request(config);
  }

  return Promise.reject(error)
})

const refreshToken = async () => {
  // const refreshToken = localStorage.getItem('refreshToken')
  try {
    const {data} = await apiClient.get(`/api/users/refresh`, {
      // headers: { auth: `Bearer ${refreshToken}` },
    })
    // console.log(data);
    localStorage.setItem('accessToken', data.token);
    // console.log('suc: refresh');
  }
  catch(error){
    // console.log('ref err:');
    // console.log(error);
    const { data, status } = error.response
    // console.log(status);
    if(status === 401) {
    //   console.log('refresh exp');
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    alert('Session Expired\nPlease sign in again.')
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
