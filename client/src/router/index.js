import {
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from 'vue-router'

import routes from './routes'

const history = createWebHistory('/us2/')

const router = createRouter({ history, routes })

const isAuthenticated = () => localStorage.getItem('jwt') !== null

router.beforeEach(async (to, from) => {
  if(to.name === 'Home') return
  if(!isAuthenticated() && to.name !== 'Authentication' ) return { name: 'Authentication' }
})

export default router
