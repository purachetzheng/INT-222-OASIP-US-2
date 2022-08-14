<script setup>
// import { getEvent } from '../../services/axios/api/event'
// import apiEvent from '../../services/axios/api/event'
import { apiEvent } from '../../../services/axios/api'
import { EventCard } from '../../../modules/event/components/';
import { onBeforeMount, ref, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import EventFilter from './eventFilter.vue';


const router = useRouter()
const events = ref([])

const getEvents = async (page) => {
  try {
    const { data, status } = await apiEvent.get({ page: page, pageSize: 12 })
    events.value = data.content
    console.log(data);
    return data.content
  } catch (error) {
    console.log(error);
    console.log(error.response);
    const res = error.response
    console.log(res.status)
    console.log("error ", error.message)
  }
}

onBeforeMount(async () => {
  getEvents()
})
const eventDetail = {
  visible: false,

}
</script>
 
<template>

  <main class="flex flex-col gap-4">
    <h1 class="text-center text-2xl font-bold">All Event</h1>
    <EventFilter />
    <ul id="event-list" class="grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-2">
      <EventCard v-for="event in events" :event="event"
        @click-event-card="router.push({ name: 'EventDetail', params: { eventId: event.id } })" />
    </ul>
    <h2 v-show="events.length === 0">No Scheduled Events</h2>
    
    <router-view></router-view>
    <!-- <Suspense>
        <template #default>
          <EventList :get-events="getEvents" />
        </template>
        
  
        <template #fallback>
          <div>Loading ...</div>
        </template>
      </Suspense> -->
  </main>

</template>
 
<style>
</style>