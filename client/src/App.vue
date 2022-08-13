<script setup>
import { Navbar } from './layouts'
</script>
 
<template>
  <div class="h-screen w-full flex flex-col bg-gray-100 dark:bg-gray-700">
    <Navbar />

    <router-view class="myContainer py-4"></router-view>

  </div>
</template>
 
<style>
</style>