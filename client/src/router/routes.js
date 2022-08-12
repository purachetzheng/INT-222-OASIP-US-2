export default [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/Home/index.vue'),
    },
    {
        path: '/schedules',
        name: 'Schedules',
        component: () => import('../views/Schedules/index.vue'),
    },
]
