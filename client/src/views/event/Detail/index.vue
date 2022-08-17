<script setup>
import { onBeforeMount, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { apiEvent } from '../../../services/axios/api'
import { isFuture, formatDatetime } from '../../../utils/dateTime'
import {
  IcClockOutline,
  IcHourglassEmpty, IcCalendarRound
} from '../../../assets/icons/time-icons'
import { IcEmailOutline } from '../../../assets/icons/social-icons'
import IcPersonOutline from '../../../assets/icons/social-icons/IcPersonOutline.vue'

import EditEventModal from './EditEventModal.vue'

const { params } = useRoute()
const router = useRouter()
const event = ref({
  category: {},
})
const { eventId } = params

const getEvent = async () => {
  try {
    const { data, status } = await apiEvent.getById(eventId)
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
    console.log(error.message);
    const {data, status} = error.response
    alert(data.message)
  }
}

onBeforeMount(async () => {
  getEvent()
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
      :visible="editModal.visible.status"
      :event-datetime="event.startDateTime"
      :eventNotes="event.notes"
      @close-modal="editModal.visible.off()"
      @edit-event="submitEdit"
    />
  </main>
</template>

<style></style>
