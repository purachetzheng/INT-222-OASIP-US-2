import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import useDarkMode from './darkMode'

export const useAppStore = defineStore('app', () => {

    const darkMode = useDarkMode()

    // const darkMode = ref(false)
    const sidebarExpanded  = ref(true)

    const init = () => {
      darkMode.init()
    }

    // const darkModeToggle = () => darkMode.value = !darkMode.value
    const sidebarToggle = () => sidebarExpanded.value = !sidebarExpanded.value
  
    return { 
        darkMode, 
        sidebarExpanded, 
        init,
        sidebarToggle,
    }
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useAppStore, import.meta.hot))
}
