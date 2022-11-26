import router from '../../router'
import { loginRequest } from '../../authConfig'
import { useIsAuthenticated } from '../../services/MSAL/composition-api/useIsAuthenticated'
import { useUserStore } from '.'
import { storeToRefs } from 'pinia'
export default function useAuthMsal (instance, accounts) {

    const loginWithMS = useIsAuthenticated()
    const userStore = useUserStore()

    const msalSetUser = async () => {
        const account = accounts.value[0].idTokenClaims
        const { name, preferred_username: email, roles } = account
        const role = roles ? roles[0] : 'guest'
        userStore.setUser({ name, email, role, auth: 1 })
    }

    const msalSignOut = async () => {
        instance.logoutPopup({
            mainWindowRedirectUri: import.meta.env.VITE_MS_LOGOUT_REDIRECT_URI,
        })
        // return router.push({ name: 'Authentication'})
    }

    const msalSignIn = async () => {
        console.log('sign in with MS account')
        try {
            const res = await instance.loginPopup(loginRequest)
            msalSetUser()
            router.push({ name: 'Home' })
        } catch (error) {
            console.log(error)
        }
    }
    // const
    return { msalSignOut, msalSignIn, msalSetUser }
}
