<script setup>
import { Navbar } from './layouts'
import { useDarkmode } from '@/stores'
import { storeToRefs } from 'pinia'
const myDarkmode = useDarkmode()
myDarkmode.init()

console.log(import.meta.env.VITE_TEST);
</script>
 
<template>
  <div class="min-h-screen w-full flex flex-col bg-gradient-to-b from-slate-300 to-slate-100 dark:bg-gray-700">
    <Navbar />
    <!-- <TheNavBar /> -->
      <router-view></router-view>
  </div>
</template>
 
<style>

::-webkit-scrollbar {
  /* width: 10px; */
  width: 0.5rem;
}
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-color: #3b82f6;
  /* background-image: linear-gradient(rgb(37, 99, 235), rgb(168, 85, 247), rgb(216, 180, 254)) */
}
::-webkit-scrollbar-thumb:hover {
  background: #2563eb;
}
::-webkit-scrollbar-track {
  /* margin: 10px 0; */
  background-color: #dbdbdb;
  border-radius: 10px;
}
</style>