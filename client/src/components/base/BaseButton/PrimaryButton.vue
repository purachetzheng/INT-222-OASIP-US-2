<script setup>
defineEmits([])
const props = defineProps({
  title: {
    type: String,
    default: 'Button',
  },
})
</script>

<template>
  <button class="btn btn-primary">
    <slot></slot>
  </button>
</template>

<style scoped>
.btn {
  @apply px-6 py-2 border-2 rounded-lg font-semibold select-none;
}
.btn:disabled {
  @apply text-white bg-gray-400 border-gray-400;
}
.btn-indigo {
  @apply text-white bg-indigo-500 border-indigo-500;
}
.btn-indigo:enabled:hover {
  @apply bg-indigo-600 border-indigo-600;
}
</style>
