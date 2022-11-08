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
    {
        path: '/users',
        name: 'Users',
        component: () => import('../views/user/Users/index.vue'),
    },
    {
        path: '/test',
        name: 'Test',
        component: () => import('../views/test/index.vue'),
    },
    {
        path: '/authentication',
        name: 'Authentication',
        component: () => import('../views/user/Authentication/index.vue'),
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About/index.vue'),
    },
    {
        path: '/event-category-list',
        name: 'EventCategoryList',
        component: () => import('../views/eventCategory/eventCategoryList/index.vue'),
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
];
