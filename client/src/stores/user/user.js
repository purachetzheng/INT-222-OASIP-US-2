import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
export const useUser = defineStore('user', () => {
  const user = ref(null)
  const isLogin = computed(() => user.value !== null)
  const loginUser = (user) => (user.value = user)
  return { user, isLogin, loginUser }
})
if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useUser, import.meta.hot))
}
