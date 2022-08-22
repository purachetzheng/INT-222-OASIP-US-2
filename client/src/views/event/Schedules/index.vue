<script setup>
import { apiEvent } from '@/services/axios/api'
// import { EventCard } from '@/modules/event/components/'
import EventCard from '@/modules/event/components/eventCard/index.vue'
import Loading from './Loading.vue'
import { onBeforeMount, ref, reactive, onMounted, defineAsyncComponent } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import EventFilter from './eventFilter.vue'
import Pagination from './Pagination.vue'


const router = useRouter()
const events = ref([])
const pageInfo = reactive({
  number: 0,
  totalPages: 2,
  toPage(number) {
    // console.log(number)
    events.value = []
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
    // console.log(error)
    // console.log(error.response)
    const res = error.response
    console.log(res.status)
    console.log('error ', error.message)
  }
}
// await getEvents()
onBeforeMount(async () => {
  // await getEvents()
  getEvents()
})
// const AsyncEventCard = defineAsyncComponent({
//   // loader: async () => import("@/modules/event/components/eventCard/index.vue" /* webpackChunkName: "event" */ ),
//   loader: () => new Promise((resolve) => {
//     setTimeout(() => {
//       resolve(import("@/modules/event/components/eventCard/index.vue" /* webpackChunkName: "event" */ ))
//     }, 1000)}),
//   loadingComponent: Loading,
//   delay: 200,
//   // suspensible: false,
// })
</script>

<template>
  <main
    class="my-container h-full flex flex-col py-4 gap-4 justify-between test"
  >
    <h1 class="text-center text-2xl font-bold">Schedules</h1>
    <EventFilter />
    <TransitionGroup name="event-list" tag="ul"
      id="event-list"
      class="h-full auto-rows-min grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-4"
    >
      
      <EventCard
        v-for="event in events" :key="event.id"
        :event="event"
        @click-event-card="
          router.push({ name: 'EventDetail', params: { eventId: event.id } })"
      />
      <!-- <component :is="AsyncEventCard" v-for="event in events" :key="event.id" :event="event" /> -->
      
    
    </TransitionGroup>

    <h2 v-show="events.length === 0">No Scheduled Events</h2>
    <Pagination
      :page="pageInfo.number"
      :total="pageInfo.totalPages"
      @emit-to-page="pageInfo.toPage"
      @emit-next-page="pageInfo.nextPage"
      @emit-prev-page="pageInfo.prevPage"
    />
  </main>
</template>

<style>

</style>
