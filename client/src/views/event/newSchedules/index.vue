<script setup>
import { onBeforeMount, ref } from 'vue'
import BaseDropdown from '../../../components/base/BaseDropdown/index.vue'
import { apiEvent, apiEventCategory } from '../../../services/api/lib'
import FilterBar from './components/FilterBar.vue'
import EventCard from './components/EventCard.vue';
const events = ref([])
const getEvents = async ({
  page,
  sortBy,
  eventCategoryID,
  dateStatus,
  date,
} = {}) => {
  try {
    const { data, status } = await apiEvent.get({
      page: page,
      pageSize: 5,
      sortBy,
      eventCategoryID,
      dateStatus,
      date,
    })
    const { content, number, totalPages } = data
    events.value = content
  } catch (error) {
    // console.log(error)
    // console.log(error.response)
    const res = error.response
    // console.log(res.status)
    console.log('error ', error.message)
  }
}
// await getEvents()
onBeforeMount(async () => {
  // await getEvents()
  getEvents()
})
</script>

<template>
  <main
    class="my-container h-full flex flex-col py-8 gap-8 justify-between test"
  >
    <!-- <h1 class="text-center text-3xl font-bold">Booking</h1> -->
    <FilterBar />
    <TransitionGroup
      name="event-list"
      tag="ul"
      id="event-list"
      class="h-full auto-rows-min grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-4"
    >
      <EventCard
        v-for="event in events"
        :key="event.id"
        :event="event"
      />
    </TransitionGroup>
  </main>
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
