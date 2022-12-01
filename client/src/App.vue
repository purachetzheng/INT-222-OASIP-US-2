<script setup>
import { Navbar } from './layouts'
import { useUserStore, useAppStore } from './stores'
import { storeToRefs } from 'pinia'
const appStore = useAppStore()
const userStore = useUserStore()
// userStore.getUserInfo()
userStore.init()
appStore.init()
// appStore.expandSidebar()
console.log(appStore.sidebarCollapsed);
// console.log(Boolean(appStore.getSidebarCollapsed()));
// console.log();
</script>
 
<template>
  <!-- <div class="min-h-screen w-full flex flex-col bg-gradient-to-b from-slate-300 to-slate-100 dark:bg-gray-700"> -->
    <!-- <Navbar /> -->
    <!-- <TheNavBar /> -->
    <router-view></router-view>
  <!-- </div> -->
</template>
 
<style>
#app {
  height: 100vh;
  /* position: relative; */
}


</style>