<script setup>
import { computed } from 'vue'

defineEmits([])
const props = defineProps({
  category: {
    type: Object,
    require: true,
  },
  selected: {
    type: Boolean,
    default: false,
  },
})
const category = computed(() => {
  return {
    id: props.category.eventCategoryID,
    name: props.category.eventCategoryName,
    description: props.category.eventCategoryDescription,
    duration: props.category.eventDuration,
  }
})
</script>

<template>
  <li
    class="p-4 flex flex-col cursor-pointer rounded-md shadow-lg"
    :class="[selected ? 'bg-gray-200 ' : 'bg-white hover:bg-gray-100']"
  >
    <h5 class="whitespace-nowrap">{{ category.name }}</h5>
    <p class="">{{ category.duration }} min</p>
  </li>
</template>

<style></style>
