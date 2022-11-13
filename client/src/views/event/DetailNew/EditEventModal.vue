<script setup>
import { computed, onBeforeMount, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Form, Field, ErrorMessage, useForm } from 'vee-validate'

import * as yup from 'yup'
import {
  isFuture,
  isBefore,
  isFutureOrSameDay,
  formatDatetime,
} from '../../../utils/dateTime'
import IcArrowRightBold from '../../../assets/icons/arrows-icons/IcArrowRightBold.vue'
import BaseModal from '../../../components/base/BaseModal.vue'
import InputField from '../../../components/base/form/InputField.vue'
import TextAreaField from '../../../components/base/form/TextAreaField.vue'

// import '../../../services/validation/YupCustomValidations'
import { apiEvent } from '@/services/api/lib'
// import EditEventSchema from '../../../services/validation/schema/EditEventSchema'
const { params } = useRoute()
const { eventId } = params

const emits = defineEmits(['closeModal', 'editEvent'])
const props = defineProps({
  categoryID: {
    type: Number,
    require: true,
  },
  datetime: {
    type: String,
    require: true,
  },
  notes: {
    type: String,
    require: true,
  },
  duration: {
    type: Number,
    require: true,
  },
  show:{
    type: Boolean,
    default: false
  }
})

const event = ref({
  categoryId: props.categoryID,
  startDate: formatDatetime.dayMonthYear(props.datetime),
  startTime: formatDatetime.hourTime(props.datetime),
  inputDate: formatDatetime.inputDate(props.datetime),
  inputTime: formatDatetime.inputTime(props.datetime),
  notes: props.notes,
})

const timePool = ref([])
const updateTimePool = async (inputDate, id = props.eventCategoryID) => {
  try {
    const param = {
      keyword: inputDate,
      eventCategoryID: id,
    }
    // console.log(param)
    const { data } = await apiEvent.getAll(param)
    timePool.value = data
    console.log('timePool: ', timePool.value)
  } catch (error) {}
}
const isOverlap = () => {
  console.log(timeRef.value)
  console.log(dateRef.value)
}

const schema = {
  date(value) {
    if (!isFutureOrSameDay(value)) {
      return 'Start Time must be future'
    }
    if (isOverlap()) {
      return 'Start Time must not be overlap'
    }
    return true
  },
  time(value) {
    if (isOverlap()) {
      return 'Start Time must not be overlap'
    }
    return true
  },
  notes(value) {
    if (value && value.trim() === '') {
      return 'Notes must not be blank'
    }
    return true
  },
}
const { handleSubmit, errors, setValues, useFieldModel } = useForm({
  validationSchema: schema,
  // initialTouched: {
  //   email: true, // touched
  //   notes: false, // non-touched
  // },
  // initialErrors: {
  //   email: 'This email is already taken',
  //   password: 'The password is too short',
  // },
  keepValuesOnUnmount: false,
})
setValues({
  date: event.value.inputDate,
  time: event.value.inputTime,
  notes: event.value.notes,
})
const [dateRef, timeRef, notes] = useFieldModel(['date', 'time', 'notes'])
const onSubmit = handleSubmit(({ date, time, notes }) => {
  const data = {
    eventStartTime: formatDatetime.jsonDatetime(date, time),
  }
  if (!!notes) data.eventNotes = notes.trim()
  emits('editEvent', data)
})

onMounted(() => {
  updateTimePool(event.value.inputDate, event.value.categoryId)
})
</script>

<template>

    <BaseModal :show="show" @close="$emit('closeModal')" :width="'w-160'">
      <template #header>
        <h1 class="text-2xl font-semibold text-center">Edit Event</h1>
      </template>

      <template #body>
        <div class="flex flex-col gap-4">
          <div class="grid grid-cols-8 items-center">
            <p class="col-span-7 font-medium text-sm">Date</p>
            <p class="text-right text-red-500 text-sm">*</p>
            <p class="col-span-3 text-lg font-semibold">
              {{ datetime.startDate }}
            </p>
            <IcArrowRightBold class="col-span-1 w-6 h-6" />
            <InputField
              class="col-span-4"
              name="date"
              type="date"
              @change="updateTimePool($event.target.value)"
            />
          </div>

          <div class="grid grid-cols-8 items-center">
            <p class="col-span-7 font-medium text-sm">Time</p>
            <p class="text-right text-red-500 text-sm">*</p>
            <p class="col-span-3 text-lg font-semibold">
              {{ datetime.startTime }}
            </p>
            <IcArrowRightBold class="col-span-1 w-6 h-6" />
            <InputField
              class="col-span-4"
              name="time"
              type="time"
            />
          </div>

          <div class="flex flex-col">
            <p class="font-medium text-sm mb-2">Notes</p>
            <TextAreaField
              class="col-span-4"
              name="notes"
            />
          </div>
        </div>
      </template>

      <template #footer>
        <div class="flex justify-center gap-4">
          <button @click="onSubmit" class="bg-blue-500 p-2">Edit</button>

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

</template>

<style scoped>
.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}</style>
