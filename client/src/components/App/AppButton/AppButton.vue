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
  },
  outline: Boolean,
  ghost: Boolean
})
</script>

<template>
  <button class="" :class="[`btn ${btnType} btn-${btnSize}`, props.outline && 'btn-outline', props.ghost && 'btn-ghost']" >
    <fa-icon :icon="props.btnIcon" v-if="props.btnIcon !== null" class="pr-2" />
    <slot></slot>
  </button>
</template>

<style scoped>
@import url('buttonColor.css');

.btn {
  /* @apply border-2 rounded-md box-border font-medium select-none duration-300 items-center; */
  @apply px-4 py-2 outline-none rounded-md text-white font-medium duration-300 transition-colors items-center;
}
.btn:enabled:active{
  @apply scale-95
}
.btn:disabled {
  /* @apply text-white bg-gray-400 border-gray-400; */
  @apply bg-gray-400/80 shadow-none cursor-not-allowed !important;
}
/* .btn:focus{
  @apply bg-indigo-600 ring-2 ring-indigo-600 ring-offset-2
} */
.btn-outline{
  @apply py-1.5 border-2 bg-transparent !important;
}
.btn-outline:hover{
  @apply border-transparent !important;
}
.btn-ghost{
  @apply bg-transparent !important;
}
.btn-ghost:hover{
  @apply border-transparent !important;
}
/* [class*="outline"]{
  padding-top: 0.4375rem;
  padding-bottom: 0.4375rem;
} */
/* .btn-sm{
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
} */


.no-border {
  @apply border-transparent
}
</style>
