<script setup>
import { computed, onBeforeMount, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import { getEvent } from '../../../services/api/lib/event'
import FilterBar from './components/FilterBar.vue'
import EventCard from './components/EventCard.vue';
import PageLoader from '../../../components/shared/Loading/PageLoader.vue'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'
const router = useRouter()

const isLoading = ref(false)
const events = ref([])

const pageInfoTemplate = reactive({
  last: '',
  number: 0
})

const pageInfo = ref({...pageInfoTemplate})

const pageParams = reactive({
  eventCategoryId: null,
  dateStatus: 'all',
  date: null,
  page: 0
})
const filterSettingHandler = {
  set: (obj, prop, value) => {
    if (prop === 'eventCategoryId' && value === obj.eventCategoryId) {
      obj[prop] = null
    } else {
      obj[prop] = value
    }
    getEvents()
    return true
  }
}
const filterSettingProxy = new Proxy(pageParams, filterSettingHandler)

const getEvents = async (params) => {
  try {
    isLoading.value = true;
    const { data, status } = await getEvent({...params, pageSize: 200, ...pageParams})
    const { content, ...other } = data
    pageInfo.value = other
    events.value = content
    // events.value.push(...content)
  } catch (error) {
    const res = error.response
    // console.log(res.status)
    console.log('error ', error.message)
  }
  finally{
    isLoading.value = false;
  }
}

onBeforeMount(async () => {
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
  
  <!-- <main
    class="my-container h-full flex flex-col py-8 gap-8 justify-between test"
  > -->
  <PageWrapper>
    <PageLoader v-if="isLoading" />
    <!-- <h1 class="text-center text-3xl font-bold">Booking</h1> -->
    <FilterBar :filter-setting="filterSettingProxy" />
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
        @click-event-card="router.push({ name: 'EventDetail', params: { eventId: event.id } })"
      />
    </TransitionGroup>
    <div class="flex justify-center" v-if="!pageInfo.last">
      <button class="font-semibold " @click="filterSettingProxy.page++">
        <p class="">
          See More
        </p>
        <fa-icon :icon="['fas', 'chevron-down']" class="fa-lg"  />
      </button>
    </div>
  </PageWrapper>
  <!-- </main> -->
</template>

<style scoped>
</style>