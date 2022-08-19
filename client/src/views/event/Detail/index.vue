<script setup>
import { defineAsyncComponent, onBeforeMount, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { apiEvent } from '../../../services/axios/api'
import { isFuture, formatDatetime } from '../../../utils/dateTime'

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
  visible: {
    status: false,
    on() {
      this.status = true
    },
    off() {
      this.status = false
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

onBeforeMount(async () => {
  getEvent()
})

const EditEventModal = defineAsyncComponent({
  loader: () => import('./EditEventModal.vue'),

  // A component to use while the async component is loading
  // loadingComponent: LoadingComponent,
  // Delay before showing the loading component. Default: 200ms.
  delay: 200,

  // A component to use if the load fails
  // errorComponent: ErrorComponent,
  // The error component will be displayed if a timeout is
  // provided and exceeded. Default: Infinity.
  timeout: 3000,
})
</script>

<template>
  <main class="my-container py-4 gap-4 grid grid-cols-12 px-20">
    <!-- <h1 class="text-3xl font-bold text-center">Event Detail</h1> -->

    <div class="col-span-12">
      <button @click="editModal.visible.on">Edit</button>
      <button @click="editModal.visible.off">Edit</button>
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

    <EditEventModal
      v-if="editModal.visible.status"
      :datetime="event.startDateTime"
      :notes="event.notes"
      :categoryID="event.category.id"
      :duration="event.duration"
      @close-modal="editModal.visible.off()"
      @edit-event="submitEdit"
    />
  </main>
</template>

<style></style>
