<script setup>
// import { getEvent } from '../../services/axios/api/event'
// import apiEvent from '../../services/axios/api/event'
import { apiEvent } from '../../../services/axios/api'
import { EventCard } from '../../../modules/event/components/'
import { onBeforeMount, ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import EventFilter from './eventFilter.vue'
import Pagination from './Pagination.vue'
import { IcClock, IcClockOutline } from '../../../assets/icons/time-icons'
import { IcArrowRight, IcArrowRightBold } from '../../../assets/icons/arrows-icons'
const router = useRouter()
const events = ref([])
const pageInfo = reactive({
  number: 0,
  totalPages: 2,
  toPage(number) {
    console.log(number)
    getEvents(number)
    window.scrollTo(0,0);
  },
  nextPage() {
    getEvents(pageInfo.number + 1)
  },
  prevPage() {
    getEvents(pageInfo.number - 1)
  },
})

const getEvents = async (page) => {
  try {
    const { data, status } = await apiEvent.get({ page: page, pageSize: 20 })
    const { content, number, totalPages } = data
    events.value = content
    pageInfo.number = number
    pageInfo.totalPages = totalPages
    return data.content
  } catch (error) {
    console.log(error)
    console.log(error.response)
    const res = error.response
    console.log(res.status)
    console.log('error ', error.message)
  }
}

onBeforeMount(async () => {
  getEvents()
})
</script>

<template>
  <main
    class="myContainer h-full flex flex-col py-4 gap-4 justify-between "
  >
 
    <h1 class="text-center text-2xl font-bold">All Bookings</h1>
    <EventFilter />
    <ul
      id="event-list"
      class="h-full auto-rows-min grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-2"
    >
      <EventCard
        v-for="event in events"
        :event="event"
        @click-event-card="
          router.push({ name: 'EventDetail', params: { eventId: event.id } })"
      />
    </ul>
    
    <!-- <Pagination
      :page="pageInfo.number"
      :total="pageInfo.totalPages"
      @emit-to-page="pageInfo.toPage"
      @emit-next-page="pageInfo.nextPage"
      @emit-prev-page="pageInfo.prevPage"
    /> -->
    <h2 v-show="events.length === 0">No Scheduled Events</h2>
  </main>
</template>

<style></style>
