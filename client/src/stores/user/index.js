import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import { apiAuth, apiUser } from '../../services/api/lib'
import router from '../../router'

import useAuthMsal from './authMsal'

import { useMsal } from '../../services/MSAL/composition-api/useMsal'
import { loginRequest } from '../../authConfig'
import { useIsAuthenticated } from '../../services/MSAL/composition-api/useIsAuthenticated'
import { getToken, setToken, removeToken } from './authToken'

const userTemplate = {
    name: '',
    email: '',
    role: '',
    auth: false,
}

export const useUserStore = defineStore('user', () => {
    const user = ref({ ...userTemplate })
    const setUser = (userInfo) => (user.value = userInfo)

    const role = computed(() => user.value.role)

    const authWith = computed(() => localStorage.getItem('auth-with'))

    const isLoading = ref(false)

    const isSignedIn = computed(() => user.value.auth);

    const { instance, accounts } = useMsal()

    const isAuth = computed(() => user.value.auth)

    const loginWithMS = useIsAuthenticated()
    // const checkUser = () => {}
    const authMsal = useAuthMsal(instance, accounts)

    const init = async () => {
        const accessToken = localStorage.getItem('accessToken')

        if (loginWithMS.value) {
            console.log('🔑 you already sign in with MS account')
            authMsal.msalLoadUser()
            authMsal.msalGetToken()
            return
        }
        if (accessToken) {
            console.log('🔑 you already sign in with OASIP account')
            getUserInfo()
        }
    }
    const getUserInfo = async () => {
        const existToken = getToken()
        if (!existToken) return
        try {
            // const {data} = await apiUser.getById(1)
            const { data } = await apiAuth.get()
            user.value = data
            user.value.auth = true
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
            setToken(data.accessToken)
            // localStorage.setItem('accessToken', data.accessToken)
            getUserInfo()
        } catch (error) {
            return Promise.reject(error)
        }
    }
    
    const logout = async () => {
        if (loginWithMS.value) return authMsal.msalSignOut()
        try {
            const { data } = await apiAuth.logout()
            removeToken()
            // localStorage.removeItem('accessToken')
            user.value = { ...userTemplate }
            alert('sign out')
            return router.push({ name: 'Authentication' })
        } catch (error) {
            return Promise.reject(error)
        }
    }

    const registerUser = async () => {}
    // const setToken = (token, type = 'oasip') => {
    //     localStorage.setItem('auth-with', type)
    //     localStorage.setItem('accessToken', token)
    // }
    // const removeToken = () => {
    //     localStorage.removeItem('auth-with')
    //     localStorage.removeItem('accessToken')
    // }
    const refreshToken = async () => {}

    return {
        user,
        authWith,
        isSignedIn,
        init,
        isAuth,
        role,
        getUserInfo,
        logout,
        login,
        setUser,
        getToken,
        ...authMsal,
    }
})
if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useUserStore, import.meta.hot))
}

// export default useUserStore
