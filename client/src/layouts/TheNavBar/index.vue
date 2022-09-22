<script setup>
import { IcCalendarRound } from '@/assets/icons/time-icons'
import { IcPlusCircleOutline } from '@/assets/icons/editing-icons'
import { NavIItemDropdown, DarkmodeToggle } from './components'
import navItemList from './navItemList'
import SignInSignUp from './components/SignInSignUp.vue';
import { useUserStore } from '../../stores';
import UserMenu from './components/UserMenu.vue';
const userStore = useUserStore()
</script>

<template>
  <header class="w-full h-14 bg-gray-100 dark:bg-transparent backdrop-blur-md bg-opacity-50">
    <div class="my-container flex items-center h-full">
      <section class="grow basis-1/3 font-bold text-2xl">
        <router-link :to="{ name: 'Home' }"> OASIP </router-link>
      </section>
      <nav class="grow basis-1/3 flex justify-center">
        <ul
          class="flex gap-2 justify-center w-fit "
        >
        <!-- p-2 border-2 shadow-md rounded-2xl bg-gray-100 -->
          <NavIItemDropdown
            v-for="navItem in navItemList"
            :mainItem="navItem.mainItem"
            :subItems="navItem.subItems"
          />
        </ul>
      </nav>
      <section class="grow basis-1/3 flex items-center gap-4 justify-end ">
        <DarkmodeToggle />
        <UserMenu v-if="userStore.isAuth" />
        <SignInSignUp v-else />
      </section>
    </div>
  </header>
</template>

<style scoped>
.test {
  @apply bg-red-500;
}
</style>
