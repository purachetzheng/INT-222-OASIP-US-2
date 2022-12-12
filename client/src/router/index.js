import { storeToRefs } from 'pinia'
import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user'

import routes from './routes'

const history = createWebHistory('/us2/')

const router = createRouter({ history, routes })

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore()
    if(!from.name) await userStore.init()
    const { user, isSignedIn } = storeToRefs(userStore)
    console.log('🚦 Navigating from', from.name, 'to', to.name)
    if (!to.meta.requiresAuth) 
        next()
    else if (!isSignedIn.value) 
        next({ name: 'SignIn' })
    else if (!to.meta.allowedRole.some((role) => role == user.value.role))
        next({ name: 'Home' })
    else 
        next()
})

export default router
