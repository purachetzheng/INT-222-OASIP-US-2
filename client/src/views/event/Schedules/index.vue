<script setup>
import { computed, onBeforeMount, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import apiEvent, { getEvent, deleteEvent } from '../../../services/api/lib/event'
import FilterBar from './components/FilterBar.vue'
import EventCard from './components/EventCard.vue'
import PageLoader from '../../../components/shared/Loading/PageLoader.vue'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'
import EventDetailSlideOver from './EventDetailSlideOver.vue'
import AppSlideOver from '../../../components/App/AppSlideOver.vue'

const router = useRouter()

const isLoading = ref(false)
const events = ref([])

const pageInfoTemplate = reactive({
    last: '',
    number: 0,
})

const pageInfo = ref({ ...pageInfoTemplate })

const pageParams = reactive({
    eventCategoryId: null,
    dateStatus: 'all',
    date: null,
    page: 0,
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
    },
}
const filterSettingProxy = new Proxy(pageParams, filterSettingHandler)

const getEvents = async (params) => {
    try {
        isLoading.value = true
        const { data, status } = await getEvent({
            ...params,
            pageSize: 200,
            ...pageParams,
        })
        const { content, ...other } = data
        pageInfo.value = other
        events.value = content
        // events.value.push(...content)
    } catch (error) {
        const res = error.response
        // console.log(res.status)
        console.log('error ', error.message)
    } finally {
        isLoading.value = false
    }
}

const cancelEvent = async (id) => {
    console.log(id);
    try {
        const res = await deleteEvent(id)
        console.log(res.data)
        // alert('Cancel successfully')
        // router.push({ name: 'Schedules' })
        // events.value = events.value.filter((event) => (event.id = id))
        getEvents()
    } catch (error) {
        alert(error.message)
        // const { data, status } = error.response
    } finally {
        
        confirmDeleteModal.close()
    }
}

const confirmDeleteModal = reactive({
    state: false,
    selectedId: null,
    show: (id) => {
        confirmDeleteModal.state = true
        confirmDeleteModal.selectedId = id
        detailSlideOver.close()
    },
    close: () => (confirmDeleteModal.state = false),
    onConfirm: () => cancelEvent(confirmDeleteModal.selectedId),
    onCancel: () => {
        confirmDeleteModal.close()
    },
})

const editEvent = async (submitEvent) => {
    // console.log('eventData', eventData);
    const {id, ...event} = submitEvent
  try {
    const res = await apiEvent.patch(id, event)
    const data = await res.data
    console.log(res)
    getEvents()
    alert('Edit complete')
  } catch (error) {
    alert(error.message)
    // const { data, status } = error.response
    // alert(data.message)
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

const detailSlideOver = reactive({
    show: false,
    open: () => {
        detailSlideOver.show = true
    },
    close: () => {
        detailSlideOver.show = false
    },
})

const viewEventDetail = async (id) => {
    await router.push({ name: 'EventDetail', params: { eventId: id } })
    detailSlideOver.open()
    // setTimeout(()=> detailSlideOver.visible = true, 50)
}
</script>

<template>
    <!-- <main
    class="my-container h-full flex flex-col py-8 gap-8 justify-between test"
  > -->
    <PageWrapper :enable-scroll="!detailSlideOver.visible">
        <!-- Event Detail Slide Over -->
        <router-view v-slot="{ Component }">
            <AppSlideOver
                :show="detailSlideOver.show"
                @close="detailSlideOver.close"
            >
                <component
                    :is="Component"
                    :slide-over-stage="detailSlideOver"
                    @cancel-event="confirmDeleteModal.show"
                    @submit-edit="editEvent"
                />
            </AppSlideOver>
        </router-view>
        <app-modal :show="confirmDeleteModal.state" :type="'confirm'">
            <template #header>
                <h1 class="text-lg font-semibold">Delete</h1>
            </template>
            <template #body>
                <p class="font-medium">Do you want to cancel this event?</p>
            </template>
            <template #footer>
                <div class="flex justify-center gap-2">
                    <app-button
                        btn-type="outline-danger"
                        @click="confirmDeleteModal.onCancel"
                        >No</app-button
                    >
                    <app-button
                        btn-type="success"
                        @click="confirmDeleteModal.onConfirm"
                        >Yes</app-button
                    >
                </div>
            </template>
        </app-modal>
        <!-- <EventDetailSlideOver :slide-over-stage="detailSlideOver" /> -->
        <PageLoader v-if="isLoading" />
        <!-- <h1 class="text-center text-3xl font-bold">Booking</h1> -->
        <FilterBar :filter-setting="filterSettingProxy" />
        <ul id="event-list" 
            class="h-full auto-rows-min grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-4"
        >
            
        
        <!-- <TransitionGroup
            name="event-list"
            tag="ul"
            id="event-list"
            class="h-full auto-rows-min grid xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-4"
        > -->
            <EventCard
                v-for="event in events"
                :key="event.id"
                :event="event"
                @click-event-card="viewEventDetail(event.id)"
            />
        <!-- </TransitionGroup> -->
        </ul>
        <div class="flex justify-center" v-if="!pageInfo.last">
            <button class="font-semibold" @click="filterSettingProxy.page++">
                <p class="">See More</p>
                <fa-icon :icon="['fas', 'chevron-down']" class="fa-lg" />
            </button>
        </div>
    </PageWrapper>
    <!-- </main> -->
</template>

<style scoped></style>
