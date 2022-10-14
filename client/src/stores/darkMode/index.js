import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'

export const useDarkmode = defineStore('darkmode', () => {
  const darkmode = ref(localStorage.getItem('theme') === 'true')
  const init = () => (document.documentElement.classList.add(darkmode.value ? 'dark' : 'light'))
  const toggleMode = () => {
    darkmode.value = !darkmode.value
    localStorage.setItem('theme', darkmode.value)
    console.log(`Set theme to ${darkmode.value ? 'dark' : 'light'} mode`);
    document.documentElement.classList.remove('dark')
    document.documentElement.classList.remove('light')
    document.documentElement.classList.add(darkmode.value ? 'dark' : 'light')
  }
  return { darkmode, init, toggleMode }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useDarkmode, import.meta.hot))
}

// export default useDarkmode