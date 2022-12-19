import { ref } from 'vue'

export default function useDarkMode() {
    const stage = ref(false)
    const init = () => {
        stage.value = localStorage.getItem('theme') === 'true'
        document.documentElement.classList.add(stage.value ? 'dark' : 'light')
    }

    const toggle = () => {
        stage.value = !stage.value
        localStorage.setItem('theme', stage.value)
        console.log(`Set theme to ${stage.value ? '🌙' : '😎'}`)
        document.documentElement.classList.remove('dark')
        document.documentElement.classList.remove('light')
        document.documentElement.classList.add(stage.value ? 'dark' : 'light')
    }
    return { stage, init, toggle }
}
