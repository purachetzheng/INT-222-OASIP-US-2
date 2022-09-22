import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import { apiUser } from '../../services/api/lib'
export const useUserStore = defineStore('user', () => {
  const user = ref(null)
  const isLoading = ref(false)
  const isAuth = computed(() => user.value !== null)
  // const checkUser = () => {
    
  // }
  const getUserInfo = async () => {
    try{
      const {data} = await apiUser.getById(1)
      user.value = data
    }
    catch(error){
      const { data, status } = error.response
      console.log(data);
    }
  }
  const loginUser = (user) => (user.value = user)
  return { user, isAuth, loginUser, getUserInfo }
})
if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useUserStore, import.meta.hot))
}


// export default useUserStore