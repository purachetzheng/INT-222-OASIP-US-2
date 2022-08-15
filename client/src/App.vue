<script setup>
import { Navbar } from './layouts'
</script>
 
<template>
  <div class="min-h-screen w-full flex flex-col bg-gray-100 dark:bg-gray-700">
    <Navbar />
    <!-- <TheNavBar /> -->
      <router-view></router-view>

  </div>
</template>
 
<style>

::-webkit-scrollbar {
  width: 10px;
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
  margin: 10px 0;
  background-color: #dbdbdb;
  border-radius: 10px;
}
</style>