<script setup>
defineEmits([])
const props = defineProps({
  btnType: {
    type: String,
    default: 'primary',
  },
  btnIcon: {
    type: Array,
    default: null
  },
  btnSize: {
    type: String,
    default: 'md',
    validator(value){
      return ['sm','md','lg','xl'].includes(value)
    }
  }
})
</script>

<template>
  <button :class="`btn ${btnType} btn-${btnSize}`">
    <fa-icon :icon="props.btnIcon" v-if="props.btnIcon !== null" class="pr-2" />
    <slot></slot>
  </button>
</template>

<style scoped>
.btn {
  @apply border-2 rounded-md box-border font-medium select-none duration-300 items-center;
}
.btn-sm{
  @apply px-2.5 h-8 text-sm
}
.btn-md{
  @apply px-3.5 h-9 text-sm
}
.btn-lg{
  @apply px-4 h-10
}
.btn-xl{
  @apply px-5 h-12 text-lg
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
  @apply bg-gray-200 border-gray-200
}
.secondary:enabled:hover{
  @apply bg-gray-300 border-gray-300
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
.outline-primary {
  @apply text-indigo-500 border-indigo-500 
}
.outline-primary:enabled:hover {
  @apply text-white bg-indigo-600 border-indigo-600
}
.outline-danger {
  @apply text-red-500 border-red-500 
}
.outline-danger:enabled:hover {
  @apply text-white bg-red-600 border-red-600
}
.no-border {
  @apply border-transparent
}
</style>
