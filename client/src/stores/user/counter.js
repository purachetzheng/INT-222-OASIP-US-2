import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
export const useCounter = defineStore('counter', () => {
  const count = ref(1)
  const doubleCount = computed(() => count.value * 2)
  const resetCount = () => (count.value = 0)
  const addCounter = () => (count.value += 1)
  return { count, doubleCount, resetCount, addCounter }
})
if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useCounter, import.meta.hot))
}
