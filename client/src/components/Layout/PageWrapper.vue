<script setup>
import SideBar from './sidebar/SideBar.vue';

</script>

<template>
  <div class="app-container flex">
      <SideBar />
      <div class="app-content">
        <slot />
      </div>
    </div>
</template>

<style scoped>
.app-wrapper{
    @apply min-h-screen w-full flex flex-col bg-gradient-to-b from-slate-300 to-slate-100 dark:bg-gray-700
}
.app-container{
    @apply w-full mx-auto  xl:px-0 h-full
}
.app-content{
    @apply py-8 px-6 h-full 
}
</style>
