<script setup>
import { ref } from 'vue';
import { number } from 'yup';
import useDetectOutsideClick from './useDetectOutsideClick'
const emits = defineEmits(['select'])
const props = defineProps({
  // field: {
  //   type: String,
  //   require: true
  // },
  default: {
    type: String,
    require: false
  },
  options: {
    type: Array,
    require: true
  }
})
const show = ref(false)
console.log(!props.default);
// const selected = ref(!props.default ? props.options[0].name : props.default)
const selected = ref(!props.default ? props.options[0].name : {name: props.default})


const box = ref()
useDetectOutsideClick(box, () => {
  show.value = false
})

const select = (option) => {
  selected.value = option
  emits('select', option.value)
}
</script>

<template>
  <div class="relative" ref="box">
    <button
      class="block w-full h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
        @click="show = !show"  
      >
      <span class="flex justify-between items-center px-3">
        <span class="">{{selected.name}}</span>
        <fa-icon :icon="['fas', 'chevron-down']" class="fa-2xs" />
      </span>   
    </button>
    <div v-show="show"  class="absolute top-12 rounded-md py-2 bg-white w-48">
        <ul class="flex flex-col ">
            <li class="px-4 py-2 hover:bg-gray-200 cursor-pointer" v-for="option in options" @click="select(option)">
                <p class="text-sm">{{option.name}}</p>
            </li>
        </ul>
    </div>
  </div>
</template>

<style scoped>
.box-sm{
    @apply w-40
}
</style>
