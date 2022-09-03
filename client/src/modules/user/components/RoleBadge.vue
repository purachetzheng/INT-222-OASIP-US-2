<script setup>
import { roleBadgeStyle } from '@/config'
defineEmits([])
const props = defineProps({
  title: {
    type: String,
    require: true,
  },
})
</script>

<template>
  <span class="px-3 py-1.5 rounded-full font-medium" :class="roleBadgeStyle[title]">
    {{ title }}
  </span>
</template>

<style></style>
