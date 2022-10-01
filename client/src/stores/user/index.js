import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import { apiUser } from '../../services/api/lib'
import router from '../../router'
export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const isLoading = ref(false)
  const isAuth = computed(() => user.value !== null)
  // const checkUser = () => {
    
  // }
  const getUserInfo = async () => {
    // const refreshToken = localStorage.getItem('refreshToken')
    // if(!refreshToken) return
    // try{
    //   const {data} = await apiUser.getById(1)
    //   user.value = data
    // }
    // catch(error){
    //   const { data, status } = error.response
    //   console.log(data);
    // }
  }
  const signIn = async (user) => {
    try{
      const { data } = await apiUser.signIn(user)
      localStorage.setItem('accessToken', data.accessToken);
    }
    catch(error){
      return Promise.reject(error)
    }
  }
  const signOut = () => {
    localStorage.removeItem("accessToken")
    localStorage.removeItem("refreshToken")
    user.value = null
    alert('sign out')
    return router.push({ name: 'Authentication'})
  }
  const loginUser = (user) => (user.value = user)
  return { user, isAuth, loginUser, getUserInfo, signOut, signIn }
})
if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useUserStore, import.meta.hot))
}


// export default useUserStore