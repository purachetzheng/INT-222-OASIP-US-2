import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed, watch } from 'vue'
import useDarkMode from './darkMode'
import useSidebar from './sidebar'

export const useAppStore = defineStore('app', () => {
    const darkMode = useDarkMode()
    const appSidebar = useSidebar()

    const init = () => {
        darkMode.init()
    }

    return {
        darkMode,
        appSidebar,
        init,

    }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useAppStore, import.meta.hot))
}
