import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import { apiAuth, apiUser } from '../../services/api/lib'
import router from '../../router'
export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const isLoading = ref(false)
  const isAuth = computed(() => user.value !== null)
  // const checkUser = () => {
    
  // }
  const getUserInfo = async () => {
    const accessToken = localStorage.getItem('accessToken')
    if(!accessToken) return
    try{
      const {data} = await apiUser.getById(1)
      user.value = data
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
      user.value = null
      alert('sign out')
      return router.push({ name: 'Authentication'})
    }
    catch(error){
      return Promise.reject(error)
    }
  }
  const loginUser = (user) => (user.value = user)
  return { user, isAuth, loginUser, getUserInfo, logout, login }
})
if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useUserStore, import.meta.hot))
}


// export default useUserStore