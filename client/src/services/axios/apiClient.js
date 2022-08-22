import axios from 'axios'

const url = import.meta.env.VITE_BASE_URL + 'eventcategories/';
console.log(url);
const apiClient = axios.create({
  baseURL: `http://intproj21.sit.kmutt.ac.th/us2/api`,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
})
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