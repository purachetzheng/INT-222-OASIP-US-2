<script setup>
defineEmits([])
const props = defineProps({
  first: {
    type: String,
    require: true,
  },
})
</script>

<template>
  <router-link :to="{ name: 'Authentication' }" class="p-2 border-2 border-blue-500 rounded-md flex items-center gap-1 group  hover:bg-blue-500 duration-300">
    <fa-layer class="fa-xl text-blue-500 group-hover:text-white">
      <fa-icon :icon="['far', 'circle']" class="" />
      <fa-icon :icon="['fas', 'user']" class="" transform="shrink-8" />
    </fa-layer>
    <p class="text-blue-500 group-hover:text-white">Sign In</p>
  </router-link>
</template>

<style></style>
