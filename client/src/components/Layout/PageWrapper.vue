<script setup>
import SideBar from './sidebar/SideBar.vue'
// import NavBar from '../../layouts/TheNavBar/index.vue';
import NavBar from './navbar/NavBar.vue';
</script>

<template>
  <div class="app-wrapper">
    <SideBar class="" />
    <div class="app-container flex"  >
      <NavBar />
      <div class="app-content">
        <slot />
      </div>
    </div>
  </div>
</template>

<style scoped>

.app-wrapper {
  @apply h-full w-full flex bg-gray-100;
}
.app-container {
  @apply w-full flex flex-col flex-1 overflow-hidden;
}
.app-content {
  @apply py-6 px-6 h-full overflow-y-auto;
}
</style>
