<script setup>
import SideBar from './sidebar/SideBar.vue'
// import NavBar from '../../layouts/TheNavBar/index.vue';
import NavBar from './navbar/NavBar.vue';
const props = defineProps({
    enableScroll: {
        type: Boolean,
        default: true
    },
    noLayout: Boolean
})

</script>

<template>
  <div class="app-wrapper">
    <SideBar v-if="!noLayout" />
    <div class="app-container relative">
      <NavBar v-if="!noLayout" />
      <div class="app-content" :class="[enableScroll ? 'overflow-y-auto': 'overflow-y-hidden', noLayout && 'no-layout']">
        <slot />
      </div>
      <div id="sidebar-div"></div>
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
  @apply py-6 px-6 h-full ;
}
.no-layout {
  @apply py-0 px-0;
}
</style>
