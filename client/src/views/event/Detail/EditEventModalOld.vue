<script setup>
import { computed, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Form, Field, ErrorMessage } from 'vee-validate'
import {
  validateEmail,
  validateNotes,
  validateDateTime,
} from '../../../services/validater'

import { isFuture, formatDatetime } from '../../../utils/dateTime'
import IcArrowRightBold from '../../../assets/icons/arrows-icons/IcArrowRightBold.vue'
import BaseModal from '../../../components/base/BaseModal.vue'

const { params } = useRoute()
const { eventId } = params

const emits = defineEmits(['closeModal', 'editEvent'])
const props = defineProps({
  visible: {
    type: Boolean,
    default: false,
  },
  eventDatetime: {
    type: String,
    require: true,
  },
  eventNotes: {
    type: String,
    require: true,
  },
})

const datetime = computed(() => {
  return {
    startDate: formatDatetime.dayMonthYear(props.eventDatetime),
    startTime: formatDatetime.hourTime(props.eventDatetime),
    inputDate: formatDatetime.inputDate(props.eventDatetime),
    inputTime: formatDatetime.inputTime(props.eventDatetime),
  }
})

const onSubmit = ({ date, time, notes } = {}) => {
  const data = {
    eventStartTime: formatDatetime.jsonDatetime(date, time),
  }
  if (!!notes) data.eventNotes = notes.trim()
  emits('editEvent', data)
}
</script>

<template>
  <Teleport to="body">
    <BaseModal :show="visible" @close="$emit('closeModal')" :width="'w-160'">
      <template #header>
        <h1 class="text-2xl font-semibold text-center">Edit Event</h1>
      </template>

      <template #body>
        <Form
          id="edit-form"
          class="flex flex-col gap-4"
          @submit="onSubmit"
        >
          <div class="grid grid-cols-8 items-center">
            <p class="col-span-7 font-medium text-sm">Date</p>
            <p class="text-right text-red-500 text-sm">*</p>
            <p class="col-span-3 text-lg font-semibold">
              {{ datetime.startDate }}
            </p>
            <IcArrowRightBold class="col-span-1 w-6 h-6" />
            <Field
              type="date"
              class="form-input col-span-4"
              name="date"
              :rules="validateDateTime"
              :value="datetime.inputDate"
            />
            <ErrorMessage
              class="col-span-8 text-right text-red-500 text-sm"
              name="date"
            />
          </div>

          <div class="grid grid-cols-8 items-center">
            <p class="col-span-7 font-medium text-sm">Time</p>
            <p class="text-right text-red-500 text-sm">*</p>
            <p class="col-span-3 text-lg font-semibold">
              {{ datetime.startTime }}
            </p>
            <IcArrowRightBold class="col-span-1 w-6 h-6" />
            <Field
              type="time"
              class="form-input col-span-4"
              name="time"
              :rules="validateDateTime"
              :value="datetime.inputTime"
            />
            <ErrorMessage
              class="col-span-8 text-right text-red-500 text-sm"
              name="time"
            />
          </div>

          <div class="flex flex-col">
            <p class="font-medium text-sm mb-2">Notes</p>
            <Field
              as="textarea"
              class="form-control col-span-4"
              name="notes"
              :rules="validateNotes"
              :value="eventNotes"
            />
            <ErrorMessage
              class="text-right text-red-500 text-sm"
              name="notes"
            />
          </div>
        </Form>
      </template>

      <template #footer>
        <div class="flex justify-center gap-4">
          <button type="submit" form="edit-form" class="bg-blue-500 p-2">
            Edit
          </button>
          <button
            type="button"
            class="bg-red-500 p-2"
            @click="$emit('closeModal')"
          >
            Cancel
          </button>
        </div>
      </template>
    </BaseModal>
  </Teleport>
</template>

<style scoped>

</style>
