const navItemList = [
  {
    mainItem: {
      name: 'Events',
      route: 'Schedules',
    },
    subItems: [
      {
        icon: 'fa-regular fa-calendar',
        name: 'Schedules',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'Schedules',
      },
      {
        icon: 'fa-solid fa-circle-plus',
        name: 'Add Event',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'AddEvent',
      },
    ],
  },
  {
    mainItem: {
      name: 'Categories',
      route: 'EventCategoryList',
    },
    subItems: [
      {
        icon: 'fa-regular fa-calendar',
        name: 'Categories',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'EventCategoryList',
      },
      {
        icon: 'fa-solid fa-circle-plus',
        name: 'Add Category',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'Home',
      },
    ],
  },
  {
    mainItem: {
      name: 'Users',
      route: 'Users',
    },
  },
  {
    mainItem: {
      name: 'About',
      route: 'About',
    },
  },
  
]
export default navItemList
