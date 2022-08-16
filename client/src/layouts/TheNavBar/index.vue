<script setup>

import NavItems from './NavItems.vue'
import IcArrowDown from '@/assets/icons/arrows-icons/IcArrowDown.vue'
import {IcCalendarRound} from '@/assets/icons/time-icons'
import {IcPlusCircleOutline} from '@/assets/icons/editing-icons'
import {NavIItemDropdown} from './components';

const navItemDropdown = [
    {
        mainItem: {
            name: 'Events',
            route: 'Schedules'
        },
        subItems: [
            {
                icon: IcCalendarRound,
                name:'Schedules',
                desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
                route: 'Schedules'
            },
            {
                icon: IcPlusCircleOutline,
                name:'Add Event',
                desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
                route: 'Home'
            }
        ]
    },
    {
        mainItem: {
            name: 'Categories',
            route: 'Schedules'
        },
        subItems: [
            {
                icon: IcCalendarRound,
                name:'Categories',
                desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
                route: 'Schedules'
            },
            {
                icon: IcPlusCircleOutline,
                name:'Add Category',
                desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. At, ex?',
                route: 'Home'
            }
        ]
    },
    {
        mainItem: {
            name: 'About',
            route: 'Schedules'
        },
    }
] 
</script>

<template>
  <header class="w-full h-20">
    <div class="myContainer flex items-center h-full">
      <div class="grow basis-1/3 font-bold text-2xl">
        <router-link :to="{ name: 'Home' }"> OASIP </router-link>
      </div>

      <ul class="grow basis-1/3 flex justify-center">
        <div
          class="flex gap-2 justify-center w-fit p-2 border-2 shadow-md rounded-2xl"
        >
          <NavIItemDropdown v-for="navItem in navItemDropdown" :mainItem="navItem.mainItem" :subItems="navItem.subItems" />
        </div>
      </ul>

      <div class="grow basis-1/3">

      </div>
    </div>
  </header>
</template>

<style scoped>
.test {
    @apply bg-red-500;
}
</style>
