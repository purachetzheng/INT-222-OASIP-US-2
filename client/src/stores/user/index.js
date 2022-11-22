import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import { apiAuth, apiUser } from '../../services/api/lib'
import router from '../../router'

import { useMsal } from '../../services/MSAL/composition-api/useMsal';
import { loginRequest } from "../../authConfig";


const userTemplate = {
  name: '',
  email: '',
  role: ''
}
export const useUserStore = defineStore('user', () => {
  const { instance } = useMsal()

  const user = ref({...userTemplate})
  const isLoading = ref(false)
  const isAuth = computed(() => Boolean(user.value.auth))
  const role = computed(() => user.value.role)
  // const checkUser = () => {
    
  // }
  const getUserInfo = async () => {
    const accessToken = localStorage.getItem('accessToken')
    if(!accessToken) return
    try{
      // const {data} = await apiUser.getById(1)
      const {data} = await apiAuth.get()
      user.value = data
      user.value.auth = 1
      console.log(user.value );
    }
    catch(error){
      // const { data, status } = error.response
      // console.log(data);
      console.log(error);
    }
  }
  const login = async (user) => {
    try{
      const { data } = await apiAuth.login(user)
      localStorage.setItem('accessToken', data.accessToken);
      getUserInfo()
    }
    catch(error){
      return Promise.reject(error)
    }
  }
  const logout = async () => {
    try{
      const {data} = await apiAuth.logout()
      localStorage.removeItem("accessToken")
      user.value = {...userTemplate}
      alert('sign out')
      return router.push({ name: 'Authentication'})
    }
    catch(error){
      return Promise.reject(error)
    }
  }
  const loginUser = (user) => (user.value = user)

  const signInMS = async () => {
    const res = await instance.loginPopup(loginRequest)
    const name = res.account.name
    const email = res.account.username
    const role = res.idTokenClaims.roles[0]
    user.value = {name,email,role,auth: 1}
    console.log(user.value);
    // localStorage.setItem('accessToken', res.accessToken)
    router.push({ name: 'Home'})
  }
  return { user, isAuth, role, loginUser, getUserInfo, logout, login, signInMS }
})
if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useUserStore, import.meta.hot))
}


// export default useUserStore