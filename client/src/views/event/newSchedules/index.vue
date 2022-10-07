<script setup>
import { computed, onBeforeMount, reactive, ref } from 'vue'
import BaseDropdown from '../../../components/base/BaseDropdown/index.vue'
import { apiEvent, apiEventCategory } from '../../../services/api/lib'
import { getEvent } from '../../../services/api/lib/event'
import FilterBar from './components/FilterBar.vue'
import EventCard from './components/EventCard.vue';

const isLoading = ref(true)
const events = ref([])

const pageInfoTemplate = {
  last: '',
  number: 0
}

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
    const { data, status } = await getEvent({...params, pageSize: 2, ...pageParams})
    const { content, ...other } = data
    pageInfo.value = other
    // events.value = content
    events.value.push(...content)
  } catch (error) {
    const res = error.response
    // console.log(res.status)
    console.log('error ', error.message)
  }
}

onBeforeMount(async () => {
  getEvents({})
})
</script>

<template>
  <main
    class="my-container h-full flex flex-col py-8 gap-8 justify-between test"
  >
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
  </main>
</template>

<style scoped>
</style>