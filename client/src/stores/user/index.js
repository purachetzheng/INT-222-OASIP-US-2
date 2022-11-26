import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import { apiAuth, apiUser } from '../../services/api/lib'
import router from '../../router'

import useAuthMsal from './authMsal'

import { useMsal } from '../../services/MSAL/composition-api/useMsal'
import { loginRequest } from '../../authConfig'
import { useIsAuthenticated } from '../../services/MSAL/composition-api/useIsAuthenticated'

const userTemplate = {
    name: '',
    email: '',
    role: '',
}

export const useUserStore = defineStore('user', () => {
    const user = ref({ ...userTemplate })
    const setUser = (userInfo) => (user.value = userInfo)

    const role = computed(() => user.value.role)

    const isLoading = ref(false)

    const { instance, accounts } = useMsal()

    const isAuth = computed(() => Boolean(user.value.auth))

    const loginWithMS = useIsAuthenticated()
    // const checkUser = () => {}
    const authMsal = useAuthMsal(instance, accounts)

    const init = async () => {
        const accessToken = localStorage.getItem('accessToken')

        if (loginWithMS.value) {
            console.log('you already sign in with MS account')
            authMsal.msalSetUser()
            return
        }
        if (accessToken) {
            console.log('you already sign in with OASIP account')
            getUserInfo()
        }
    }
    const getUserInfo = async () => {
        const accessToken = localStorage.getItem('accessToken')
        if (!accessToken) return
        try {
            // const {data} = await apiUser.getById(1)
            const { data } = await apiAuth.get()
            user.value = data
            user.value.auth = 1
            console.log(user.value)
        } catch (error) {
            // const { data, status } = error.response
            // console.log(data);
            console.log(error)
        }
    }

    const login = async (user) => {
        try {
            const { data } = await apiAuth.login(user)
            localStorage.setItem('accessToken', data.accessToken)
            getUserInfo()
        } catch (error) {
            return Promise.reject(error)
        }
    }
    const logout = async () => {
        if (loginWithMS.value) return authMsal.msalSignOut()
        try {
            const { data } = await apiAuth.logout()
            localStorage.removeItem('accessToken')
            user.value = { ...userTemplate }
            alert('sign out')
            return router.push({ name: 'Authentication' })
        } catch (error) {
            return Promise.reject(error)
        }
    }

    const registerUser = async () => {}
    const getToken = async () => {}
    const refreshToken = async () => {}

    return {
        user,
        init,
        isAuth,
        role,
        getUserInfo,
        logout,
        login,
        setUser,
        ...authMsal,
    }
})
if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useUserStore, import.meta.hot))
}

// export default useUserStore
