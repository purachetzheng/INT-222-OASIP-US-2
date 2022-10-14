<script setup>
defineEmits([])
const props = defineProps({
  buttonType: {
    type: String,
    default: 'primary',
  },
})
</script>

<template>
  <button :class="`btn ${buttonType}`">
    <slot></slot>
  </button>
</template>

<style scoped>
.btn {
  @apply px-6 py-2 border-2 rounded-lg font-semibold select-none duration-300;
}
.btn:disabled {
  @apply text-white bg-gray-400 border-gray-400;
}
.primary{
  @apply text-white bg-indigo-500 border-indigo-500;
}

.primary:enabled:hover {
  @apply bg-indigo-600 border-indigo-600;
}
.secondary{

}
.secondary:enabled:hover{
}
.success{
  @apply text-white bg-green-500 border-green-500 
}
.success:enabled:hover{
  @apply bg-green-600 border-green-600
}
.danger{
  @apply text-white bg-red-500 border-red-500 
} 
.danger:enabled:hover{
  @apply bg-red-600 border-red-600
}
.warning{

} 
.warning:enabled:hover{

} 
.muted{

}

.outline-danger {
  @apply text-red-500 border-red-500 
}
.outline-danger:enabled:hover {
  @apply text-white bg-red-600 border-red-600
}
</style>
