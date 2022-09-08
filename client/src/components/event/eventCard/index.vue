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
    <div
      class="card-header"
      :class="[event.isFuture ? 'header-upcoming' : 'header-past']"
    >
      {{ event.isFuture ? 'Upcoming' : 'Past' }}
    </div>
    <div class="card-body">
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
      <p class="card-footer">
        View Detail
        <fa-icon :icon="['fas', 'arrow-right']" class="" />
      </p>
    </div>
  </li>
</template>

<style scoped>
@import url('style.css');
</style>
