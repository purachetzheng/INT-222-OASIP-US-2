import router from '../../router'
import { loginRequest, tokenRequest } from '../../authConfig'
import { useIsAuthenticated } from '../../services/MSAL/composition-api/useIsAuthenticated'
import { useUserStore } from '.'
import { storeToRefs } from 'pinia'
import { removeToken, setToken } from './authToken'
import { ref } from 'vue'
export default function useAuthMsal(instance, accounts) {
    const loginWithMS = ref(useIsAuthenticated())
    const userStore = useUserStore()

    const msalGetToken = async () => {
        // console.log(tokenRequest)
        // console.log(accounts.value)
        instance.setActiveAccount(accounts.value[0])
        const {accessToken, ...rest} = await instance
            .acquireTokenSilent(tokenRequest)
            .catch((error) => {
                console.warn(error)
                console.warn(
                    'silent token acquisition fails. acquiring token using popup'
                )
                // if (error instanceof msal.InteractionRequiredAuthError) {
                //     // fallback to interaction when silent call fails
                //     return myMSALObj.acquireTokenPopup(request)
                //         .then(response => {
                //             console.log(response);
                //             return response;
                //         }).catch(error => {
                //             console.error(error);
                //         });
                // } else {
                //     console.warn(error);
                // }
            })
        setToken(accessToken, 'msal')
        // console.log(accessToken);
    }

    const msalLoadUser = async () => {
        const account = accounts.value[0].idTokenClaims
        const { name, preferred_username: email, roles } = account
        const role = roles ? roles[0] : 'guest'
        userStore.setUser({ name, email, role, auth: true })
    }

    const msalSignOut = async () => {
        instance.logoutPopup({
            mainWindowRedirectUri: import.meta.env.VITE_MS_LOGOUT_REDIRECT_URI,
        })
        // return router.push({ name: 'Authentication'})
        removeToken()
    }

    const msalSignIn = async () => {
        console.log('sign in with MS account')
        try {
            const res = await instance.loginPopup(loginRequest)
            msalLoadUser()
            msalGetToken()
            router.push({ name: 'Home' })
        } catch (error) {
            console.log(error)
        }
    }
    // const
    return { msalLoadUser, msalSignIn, msalSignOut, msalGetToken }
}
