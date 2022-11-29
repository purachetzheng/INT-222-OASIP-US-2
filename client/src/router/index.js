import { storeToRefs } from 'pinia'
import {
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from 'vue-router'
import { useUserStore } from '../stores/user'

import routes from './routes'

const history = createWebHistory('/us2/')

const router = createRouter({ history, routes })

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  console.log(`🚦 navigating to ${to.name} from ${from.name}`);
  if(!to.meta.requiresAuth) next()
  else if(!userStore.authWith) next({ name: 'Authentication' })
  else if(!(to.meta.allowedRole.some(role => role == userStore.role))) next({name: 'Home'})
  else next()
})

export default router
