<script setup>
import { inject } from 'vue'
import EventCategoryCard from '../components/EventCategoryCard.vue'
defineEmits(['next-step'])
const props = defineProps({
  categories: {
    type: Array,
    default: [],
  },
})
const selectedId = inject('selectedCategoryId')
</script>

<template>
  <section class="flex flex-col justify-between">
    <ul
      class="h-full auto-rows-min grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-4"
    >
      <EventCategoryCard
        v-for="category in categories"
        :category="category"
        :selected="selectedId === category.id"
        @click="selectedId = category.id"
      />
    </ul>
    <div class="flex justify-center">
      <button
        v-show="selectedId"
        class="bg-blue-500 p-2"
        @click="$emit('next-step')"
      >
        Next
      </button>
    </div>
  </section>
</template>

<style></style>
