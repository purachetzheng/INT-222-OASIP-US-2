import { ref, watch } from 'vue'

export default function useSidebar() {

    const collapsed = ref(Boolean(+localStorage.getItem('sidebar-collapsed')))

    const toggle = () => (collapsed.value = !collapsed.value)
    
    const expand = () => collapsed.value = false
    
    const collapse = () => collapsed.value = true
    
    watch(collapsed, async (value) => localStorage.setItem('sidebar-collapsed', value ? 1 : 0))

    return {collapsed, toggle, expand, collapse}
}
