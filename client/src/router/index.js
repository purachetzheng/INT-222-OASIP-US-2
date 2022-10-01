import {
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from 'vue-router'

import routes from './routes'

const history = createWebHistory('/us2/')

const router = createRouter({ history, routes })

const isAuthenticated = () => localStorage.getItem('accessToken') !== null

const nonNeededAuth = ['Home', 'About', 'NewEvents']

router.beforeEach(async (to, from) => {
  if(nonNeededAuth.some((el) => el === to.name)) return
  if(!isAuthenticated() && to.name !== 'Authentication' ) return { name: 'Authentication' }
})

export default router
