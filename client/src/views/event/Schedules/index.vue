<script setup>
// import { getEvent } from '../../services/axios/api/event'
// import apiEvent from '../../services/axios/api/event'
import { apiEvent } from '../../../services/axios/api'
import EventCard from '../../../modules/event/components/eventCard.vue';
import { onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import EventFilter from './eventFilter.vue';
const router = useRouter()
const events = ref()
const getEvents = async () => {
  try {
    const { data, status } = await apiEvent.get()
    events.value = data.content
    console.log(data);
  } catch (error) {
    const res = error.response
    console.log(res.status)
    console.log("error ", error.message)
  }
}

onBeforeMount(() => {
  getEvents()
})
</script>
 
<template>
  <main class="flex flex-col gap-4">
    <h1 class="text-center text-2xl font-bold">All Event</h1>
    <EventFilter />
    <ul id="event-list" class="grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-2">
      <EventCard v-for="event in events" :event="event" @click-event-card="router.push({ name: 'EventDetail', params: {eventId: event.id} })" />
    </ul>
  </main>
</template>
 
<style>
</style>