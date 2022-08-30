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
    path: '/addEvent',
    name: 'AddEvent',
    component: () => import('../views/event/AddEvent/index.vue'),
    // children: [
    //   {
    //     path: 'selectCategory',
    //     name: 'SelectCategory',
    //     component: () => import('../views/event/AddEvent/nestedViews/SelectCategory.vue'),
    //   },
    //   {
    //     path: 'fillForm',
    //     name: 'FillForm',
    //     component: () => import('../views/event/AddEvent/nestedViews/FillForm.vue'),
    //   },
    // ],
  },
  {
    path: '/users',
    name: 'Users',
    component: () => import('../views/user/Users/index.vue'),
  },
  {
    path: '/sign-up',
    name: 'SignUp',
    component: () => import('../views/user/SignUp/index.vue'),
  },

]
