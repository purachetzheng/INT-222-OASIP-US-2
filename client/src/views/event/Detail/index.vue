<script setup>
import { defineAsyncComponent, onBeforeMount, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { apiEvent } from '../../../services/api/lib'
import { isFuture, formatDatetime } from '../../../utils/dateTime'
import PageLoader from '../../../components/shared/Loading/PageLoader.vue'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'

const { params } = useRoute()
const router = useRouter()
const event = ref({
  category: {},
})
const { eventId } = params
const testEvent = ref({})
const getEvent = async () => {
  try {
    const { data, status } = await apiEvent.getById(eventId)
    testEvent.value = data
    event.value = {
      id: data.id,
      name: data.bookingName,
      email: data.bookingEmail,
      category: {
        id: data.eventCategoryID,
        name: data.eventCategoryName,
      },
      duration: data.eventDuration,
      startDateTime: data.eventStartTime,
      startDate: formatDatetime.dayMonthYear(data.eventStartTime),
      startTime: formatDatetime.hourTime(data.eventStartTime),
      notes: data.eventNotes,
    }
    // event.value = data
    console.log(data)
  } catch (error) {
    const res = error.response
    event.value = 'no'
    console.log(res.status)
    console.log('error ', error.message)
  }
}

const editModal = reactive({
  preLoad: false,
  show: false,
  visible: {
    on() {
      // editModal.preLoad = true
      editModal.show = true
      // setTimeout(()=>editModal.show = true,200)
    },
    off() {
      // editModal.preLoad = false
      editModal.show = false
      // setTimeout(()=>editModal.show = false,200)
    },
  },
})

const submitEdit = async (eventData) => {
  try {
    const res = await apiEvent.patch(eventId, eventData)
    const data = await res.data
    console.log(res)
    getEvent()

    editModal.visible.off()
    alert('Edit complete')
  } catch (error) {
    console.log(error.message)
    const { data, status } = error.response
    alert(data.message)
  }
}

const cancelEvent = async () => {
  try {
    const res = await apiEvent.delete(eventId)
    console.log(res.data)
    alert('Cancel successfully')
    router.push({ name: 'Schedules' })
  } catch (error) {
    console.log(error.message)
    const { data, status } = error.response
    alert(data.message)
  }
}

onBeforeMount(async () => {
  getEvent()
})

// const EditEventModal = defineAsyncComponent({
//   loader: () => import(/* webpackPrefetch: true */'./EditEventModal.vue'),

//   delay: 2000,

//   timeout: 3000,
// })
const EditEventModal = defineAsyncComponent(async () => {
  await new Promise((resolve) => setTimeout(resolve, 1000))
  return import('./EditEventModal.vue')
})
</script>

<template>
  <PageWrapper>
    <div class="gap-4 grid grid-cols-12">
      <!-- <h1 class="text-3xl font-bold text-center">Event Detail</h1> -->

      <div class="col-span-12 flex gap-10">
        <button class="bg-blue-500 p-2" @click="editModal.visible.on">
          Edit
        </button>
        <button class="bg-red-500 p-2" @click="cancelEvent">Cancel</button>
      </div>
      <section
        class="col-span-8 grid grid-cols-3 gap-y-4 gap-x-10 p-4 bg-gray-300"
      >
        <div class="col-span-3">
          <p class="font-medium">Name</p>
          <p class="text-2xl font-semibold">{{ event.name }}</p>
        </div>
        <div class="col-span-3">
          <p class="font-medium">Email</p>
          <p class="text-lg font-semibold">{{ event.email }}</p>
        </div>
        <div class="col-span-3">
          <p class="font-medium">Category</p>
          <p class="text-lg font-semibold">{{ event.category.name }}</p>
        </div>
        <div class="col-span-3 sm:col-span-1">
          <p class="font-medium">Duration</p>
          <p class="text-lg font-semibold">{{ event.duration }} Min</p>
        </div>
      </section>
      <section class="col-span-4 p-4 bg-gray-300 grid grid-cols-2 text-center">
        <div class="">
          <p class="font-medium">Date</p>
          <p class="text-lg font-semibold">{{ event.startDate }}</p>
        </div>
        <div class="">
          <p class="font-medium">Time</p>
          <p class="text-lg font-semibold">{{ event.startTime }}</p>
        </div>
      </section>
      <section class="col-span-12 p-4 bg-gray-300 flex flex-col">
        <p class="font-medium">Note</p>
        <p>{{ event.notes }}</p>
        <!-- <BaseTextarea :text="event.note" /> -->
      </section>
      <!-- <Suspense >
      <Transition name="modal">
      <EditEventModal v-if="editModal.show"
        :show="editModal.show"
        :datetime="event.startDateTime"
        :notes="event.notes"
        :categoryID="event.category.id"
        :duration="event.duration"
        @close-modal="editModal.visible.off()"
        @edit-event="submitEdit"
      /></Transition>
      <template #fallback> Loading... </template>
    </Suspense> -->
      <Suspense>
        <EditEventModal
          :show="editModal.show"
          :datetime="event.startDateTime"
          :notes="event.notes"
          :categoryID="event.category.id"
          :duration="event.duration"
          @close-modal="editModal.visible.off()"
          @edit-event="submitEdit"
        />
        <template #fallback>
          <!-- <div class="fixed w-full h-full top-0 left-0 bg-black opacity-50 z-40"></div> -->
          <PageLoader />
        </template>
      </Suspense>
    </div>
  </PageWrapper>
</template>

<style></style>
