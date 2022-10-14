<script setup>
import { Navbar } from './layouts'
import { useDarkmode, useUserStore } from './stores'
import { storeToRefs } from 'pinia'
const myDarkmode = useDarkmode()
const userStore = useUserStore()
myDarkmode.init()
userStore.getUserInfo()
</script>
 
<template>
  <div class="min-h-screen w-full flex flex-col bg-gradient-to-b from-slate-300 to-slate-100 dark:bg-gray-700">
    <Navbar />
    <!-- <TheNavBar /> -->
    <router-view></router-view>
  </div>
</template>
 
<style>


</style>