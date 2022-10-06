<script setup>
import { computed } from 'vue'
import { isFuture, formatDatetime } from '@/utils/dateTime'

defineEmits(['clickEventCard'])
const props = defineProps({
  event: {
    type: Object,
    require: true,
  },
})
const event = computed(() => {
  return {
    id: props.event.id,
    name: props.event.bookingName,
    category: {
      id: props.event.eventCategoryID,
      name: props.event.eventCategoryName,
    },
    duration: props.event.eventDuration,
    startDate: formatDatetime.monthDayYear(props.event.eventStartTime),
    startTime: formatDatetime.hourTime(props.event.eventStartTime),
    isFuture: isFuture(props.event.eventStartTime),
  }
})
</script>
 
<template>
    <li class="card" @click="$emit('clickEventCard')">
    <!-- <div
      class="card-header"
      :class="[event.isFuture ? 'header-upcoming' : 'header-past']"
    >
      {{ event.isFuture ? 'Upcoming' : 'Past' }}
    </div> -->
    <div class="">
      <p class="text-sm text-blue-500">
        {{ event.startDate }}
      </p>
      <h5 class="card-title">
        {{ event.name }}
      </h5>
      <p class="text-sm text-gray-800">
        {{ event.category.name }}
      </p>
      <div class="flex items-center gap-2 text-sm text-gray-700">
        <fa-icon :icon="['far', 'fa-clock']" class="" />
        <p class="">
          {{ event.startTime }} - {{ event.duration }} min
        </p>
      </div>
    </div>
  </li>
</template>
 
<style scoped>

.card {
  @apply p-4 flex flex-col overflow-hidden shadow-md bg-white bg-opacity-40 font-semibold rounded-md cursor-pointer duration-150;
}

.card:hover{
  @apply bg-opacity-80
}

.card-header {
  @apply h-8 w-full flex items-center justify-center gap-1 text-white duration-300 ease-in-out;
}

.card:hover .card-header {
  @apply -mt-8;
}

.header-upcoming {
  @apply bg-blue-500;
}

.header-past {
  @apply bg-gray-400;
}

.card-body {
  @apply relative h-32 bg-white p-4 flex flex-col gap-0.5;
}

.card-footer {
  @apply absolute w-full -bottom-8 left-0 h-8 flex items-center justify-center gap-1 text-white bg-gradient-to-r from-green-400 to-blue-500;
}

.card-title {
  @apply text-lg font-bold truncate;
}
</style>