<script setup>
import { inject } from 'vue'
import EventCategoryCard from '../components/EventCategoryCard.vue'
defineEmits(['next-step'])
const props = defineProps({
  categories: {
    type: Array,
    default: [],
  },
  addEventForm:{
    type: Object,
    require: true
  }
})
const selectedId = inject('selectedCategoryId')
</script>

<template>
  <section class="flex flex-col justify-between">
    <ul
      class="h-full auto-rows-min grid xl:grid-cols-3 lg:grid-cols-2 md:grid-cols-1 grid-cols-1 gap-4"
    >
      <EventCategoryCard
        v-for="category in categories"
        :category="category"
        :selected="addEventForm.selectedCategoryId === category.id"
        @click="addEventForm.setSelectedCategoryId(category.id)"
      />
    </ul>
  </section>
</template>

<style></style>
