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
        meta: { requiresAuth: true, allowedRole: ['admin', 'lecturer', 'student'] },
        children: [
            {
                name: 'EventDetail',
                path: ':eventId',
                component: () =>
                    import('../views/event/EventDetail/EventDetail.vue'),
            },
        ],
    },
    // {
    //     path: '/schedules/:eventId',
    //     name: 'EventDetail',
    //     component: () => import('../views/event/Detail/index.vue'),
    // },
    {
        path: '/users',
        name: 'Users',
        meta: { requiresAuth: true, allowedRole: ['admin'] },
        component: () => import('../views/user/Users/index.vue'),
    },
    {
        path: '/dev',
        name: 'DevTest',
        component: () => import('../views/DevTest/DevTest.vue'),
    },
    {
        path: '/authentication',
        name: 'Authentication',
        component: () => import('../views/user/Authentication/index.vue'),
    },
    {
        path: '/about',
        name: 'About',
        component: () =>
            import(/* webpackChunkName: "about" */ '../views/About/index.vue'),
    },
    {
        path: '/event-category-list',
        name: 'EventCategoryList',
        meta: { requiresAuth: true, allowedRole: ['admin', 'lecturer', 'student'] },
        component: () =>
            import('../views/eventCategory/eventCategoryList/index.vue'),
    },
    {
        path: '/add-event',
        name: 'AddEvent',
        component: () => import('../views/event/Add-Event/AddEvent.vue'),
    },
    {
        path: '/schedules-new/:eventId',
        name: 'EventDetailNew',
        component: () => import('../views/event/DetailNew/EventDetail.vue'),
    },
]
