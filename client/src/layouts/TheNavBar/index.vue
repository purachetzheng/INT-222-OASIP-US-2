<script setup>
import NavItems from './NavItems.vue'
import IcArrowDown from '@/assets/icons/arrows-icons/IcArrowDown.vue'
import { IcCalendarRound } from '@/assets/icons/time-icons'
import { IcPlusCircleOutline } from '@/assets/icons/editing-icons'
import { NavIItemDropdown, DarkmodeToggle } from './components'

const navItemDropdown = [
  {
    mainItem: {
      name: 'Events',
      route: 'Schedules',
    },
    subItems: [
      {
        icon: IcCalendarRound,
        name: 'Schedules',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'Schedules',
      },
      {
        icon: IcPlusCircleOutline,
        name: 'Add Event',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'Home',
      },
    ],
  },
  {
    mainItem: {
      name: 'Categories',
      route: 'Schedules',
    },
    subItems: [
      {
        icon: IcCalendarRound,
        name: 'Categories',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'Schedules',
      },
      {
        icon: IcPlusCircleOutline,
        name: 'Add Category',
        desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
        route: 'Home',
      },
    ],
  },
  {
    mainItem: {
      name: 'About',
      route: 'Schedules',
    },
  },
]
</script>

<template>
  <header class="w-full h-20 bg-whit sticky top-0 z-10 bg-gray-100 dark:bg-transparent backdrop-blur-md bg-opacity-50">
    <div class="my-container flex items-center h-full">
      <section class="grow basis-1/3 font-bold text-2xl">
        <router-link :to="{ name: 'Home' }"> OASIP </router-link>
      </section>
      <nav class="grow basis-1/3 flex justify-center">
        <ul
          class="flex gap-2 justify-center w-fit p-2 border-2 shadow-md rounded-2xl bg-gray-100"
        >
          <NavIItemDropdown
            v-for="navItem in navItemDropdown"
            :mainItem="navItem.mainItem"
            :subItems="navItem.subItems"
          />
        </ul>
      </nav>
      <section class="grow basis-1/3 flex justify-end overflow-hidden ">
        <DarkmodeToggle />
      </section>
    </div>
  </header>
</template>

<style scoped>
.test {
  @apply bg-red-500;
}
</style>
