import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref, computed } from 'vue'
import { apiAuth, apiUser } from '../../services/api/lib'
import router from '../../router'

import useAuthMsal from './authMsal'

import { useMsal } from '../../services/MSAL/composition-api/useMsal'
import { loginRequest } from '../../authConfig'
import { useIsAuthenticated } from '../../services/MSAL/composition-api/useIsAuthenticated'
import { getToken, setToken, deleteToken } from './authToken'

const userTemplate = {
    name: '',
    email: '',
    role: '',
    auth: false,
}

export const useUserStore = defineStore('user', () => {
    const user = ref({ ...userTemplate })
    const setUser = (userInfo) => (user.value = userInfo)

    const isLoading = ref(false)

    const isSignedIn = computed(() => user.value.auth);

    const { instance, accounts } = useMsal()

    const isAuth = computed(() => user.value.auth)

    const loginWithMS = useIsAuthenticated()
    // const checkUser = () => {}
    const authMsal = useAuthMsal(instance, accounts)

    const init = async () => {
        const accessToken = localStorage.getItem('accessToken')
        console.log('init auth');
        if (loginWithMS.value) {
            console.log('🔑 you already sign in with MS account')
            authMsal.msalLoadUser()
            authMsal.msalGetToken()
            return
        }
        if (accessToken) {
            console.log('🔑 you already sign in with OASIP account')
            await loadUser()
        }
    }
    const loadUser = async () => {
        const existToken = getToken()
        if (!existToken) return
        try {
            // const {data} = await apiUser.getById(1)
            const { data } = await apiAuth.get()
            user.value = data
            user.value.auth = true
            // console.log(user.value)
        } catch (error) {
            // const { data, status } = error.response
            // console.log(data);
            console.log(error)
        }
    }

    const signIn = async (user) => {
        try {
            const { data } = await apiAuth.login(user)
            setToken(data.accessToken, 'oasip')
            // localStorage.setItem('accessToken', data.accessToken)
            loadUser()
            router.push({ name: 'Home' })
        } catch (error) {
            return Promise.reject(error)
        }
    }
    
    const signOut = async () => {
        if (loginWithMS.value) return authMsal.msalSignOut()
        try {
            const { data } = await apiAuth.logout()
            deleteToken()
            // localStorage.removeItem('accessToken')
            user.value = { ...userTemplate }
            alert('sign out')
            router.push({ name: 'SignIn' })
            return 
        } catch (error) {
            return Promise.reject(error)
        }
    }

    const signUp = async (registerData) => {
        try{
            const { data } = await apiUser.post(registerData)
            alert('Your user has been registered')
            router.push({ name: 'SignIn'})
        }
        catch (error) {
            return Promise.reject(error)
        }
    }
    // const setToken = (token, type = 'oasip') => {
    //     localStorage.setItem('auth-with', type)
    //     localStorage.setItem('accessToken', token)
    // }
    // const deleteToken = () => {
    //     localStorage.removeItem('auth-with')
    //     localStorage.removeItem('accessToken')
    // }
    const refreshToken = async () => {}

    return {
        user,
        isSignedIn,
        init,
        isAuth,

        loadUser,
        signIn, signUp, signOut,
        setUser,
        getToken,
        ...authMsal,
    }
})
if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useUserStore, import.meta.hot))
}

// export default useUserStore
