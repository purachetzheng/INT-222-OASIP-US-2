export default [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/Home/index.vue'),
    },
    {
        path: '/schedules',
        name: 'Schedules',
        component: () => import('../views/event/Schedules/index.vue'),
    },
    {
        path: '/schedules/:eventId',
        name: 'EventDetail',
        component: () => import('../views/event/Detail/index.vue'),
    },
]
