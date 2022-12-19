export default [
    {
        path: '/',
        name: 'Home',
        redirect: { name: 'About'}
        // component: () => import('../views/Home/index.vue'),
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
        path: '/users-old',
        name: 'UsersOld',
        meta: { requiresAuth: true, allowedRole: ['admin'] },
        component: () => import('../views/user/Users/index.vue'),
    },
    {
        path: '/users',
        name: 'Users',
        meta: { requiresAuth: true, allowedRole: ['admin'] },
        component: () => import('../views/user/UserList/index.vue'),
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
            import(/* webpackChunkName: "about" */ '../views/About.vue'),
    },
    {
        path: '/event-category-list',
        name: 'EventCategoryList',
        meta: { requiresAuth: true, allowedRole: ['admin', 'lecturer', 'student'] },
        component: () =>
            import('../views/eventCategory/eventCategoryList/index.vue'),
    },
    {
        path: '/add-event-old',
        name: 'AddEventOld',
        component: () => import('../views/event/Add-Event/AddEvent.vue'),
    },
    {
        path: '/add-event',
        name: 'AddEvent',
        component: () => import('../views/event/AddEvent/AddEvent.vue'),
    },
    {
        path: '/login',
        name: 'SignIn',
        component: () => import('../views/Authentication/SignIn.vue')
    },
    {
        path: '/register',
        name: 'SignUp',
        component: () => import('../views/Authentication/SignUp.vue')
    },
    {
        path: '/:catchNotMatchPath(.*)',
        name: 'NotFound',
        component: () => import('../views/NotFound.vue'),
    }
]
