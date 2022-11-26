import router from '../../router'
import { loginRequest, tokenRequest } from '../../authConfig'
import { useIsAuthenticated } from '../../services/MSAL/composition-api/useIsAuthenticated'
import { useUserStore } from '.'
import { storeToRefs } from 'pinia'
export default function useAuthMsal (instance, accounts) {

    const loginWithMS = useIsAuthenticated()
    const userStore = useUserStore()

    const msalGetToken = async () => {
        console.log(tokenRequest);
        console.log(accounts.value);
        instance.setActiveAccount(accounts.value[0])
        await instance.acquireTokenSilent(tokenRequest).catch(error => {
            console.warn(error);
            console.warn("silent token acquisition fails. acquiring token using popup");
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
    });
    }

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
    return { msalSetUser, msalSignIn, msalSignOut, msalGetToken }
}
