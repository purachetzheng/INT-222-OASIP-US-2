<script setup>
import SideBar from './sidebar/SideBar.vue'
import NavBar from '../../layouts/TheNavBar/index.vue';
</script>

<template>
  <div class="app-wrapper">
    <SideBar />
    <div class="app-container flex">
      <!-- <NavBar /> -->
      <div class="app-content">
        <slot />
      </div>
    </div>
  </div>
</template>

<style scoped>

.app-wrapper {
  @apply min-h-screen w-full flex bg-gradient-to-b from-slate-300 to-slate-100 dark:bg-gray-700;
}
.app-container {
  @apply w-full mx-auto xl:px-0 h-full flex flex-col;
}
.app-content {
  @apply py-8 px-6 h-full overflow-y-auto;
}
</style>
