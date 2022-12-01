import { ref } from 'vue'

export default function useDarkMode() {
    const stage = ref(localStorage.getItem('theme') === 'true')
    const init = () => {
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
