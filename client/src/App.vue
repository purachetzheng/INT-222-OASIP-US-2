<script setup>
import { Navbar } from './layouts'
</script>
 
<template>
  <div class="h-screen w-full flex flex-col bg-gray-100 dark:bg-gray-700">
    <Navbar />
    <main class="">
      <router-view></router-view>
    </main>

  </div>
</template>
 
<style>
</style>