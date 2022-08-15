<script setup>
import { computed, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Form, Field, ErrorMessage } from 'vee-validate'
import {validateEmail,validateNotes, validateDateTime} from '../../../services/validater'

import { isFuture, formatDatetime } from '../../../utils/dateTime'
import IcArrowRightBold from '../../../assets/icons/arrows-icons/IcArrowRightBold.vue'

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

const onSubmit = ({date, time, notes} = {}) => {
    const data = {
        eventStartTime: formatDatetime.jsonDatetime(date, time)
    }
    if(!!notes) data.eventNotes = notes.trim()
    emits('editEvent', data)
}

</script>

<template>
    <Transition name="fade">
        <section v-if="visible" class="absolute top-0 left-0 w-full h-full flex justify-center items-center">
            <div @click="$emit('closeModal')" class="absolute bg-black w-full h-full opacity-70 z-10"></div>
            <Form class="w-192 max-h-128 p-8 bg-gray-50 bg-opacity-80 z-30 flex flex-col gap-4 rounded-md"
                @submit="onSubmit">
                <h1 class="text-2xl font-semibold text-center">Edit Event</h1>
                <!-- date -->
                <div class="grid grid-cols-8 items-center">
                    <p class="col-span-7 font-medium text-sm">Date</p>
                    <p class="text-right text-red-500 text-sm">*</p>
                    <p class="col-span-3 text-lg font-semibold">
                        {{ datetime.startDate }}
                    </p>
                    <IcArrowRightBold class="col-span-1 w-6 h-6" />
                    <Field type="date" class="form-input col-span-4" name="date" :rules="validateDateTime" :value="datetime.inputDate" />
                    <ErrorMessage class="col-span-8 text-right text-red-500 text-sm" name="date" />
                </div>
                <!-- time -->
                <div class="grid grid-cols-8 items-center">
                    <p class="col-span-7 font-medium text-sm">Time</p>
                    <p class="text-right text-red-500 text-sm">*</p>
                    <p class="col-span-3 text-lg font-semibold">
                        {{ datetime.startTime }}
                    </p>
                    <IcArrowRightBold class="col-span-1 w-6 h-6" />
                    <Field type="time" class="form-input col-span-4" name="time" :rules="validateDateTime" :value="datetime.inputTime" />
                    <ErrorMessage class="col-span-8 text-right text-red-500 text-sm" name="time" />
                </div>
                <!-- notes -->
                <div class="flex flex-col">
                    <p class="font-medium text-sm mb-2">Notes</p>
                    <Field as="textarea" class="form-control col-span-4" name="notes" :rules="validateNotes" :value="eventNotes" />
                    <ErrorMessage class="text-right text-red-500 text-sm" name="notes" />
                </div>
                <div class="flex justify-center gap-4">
                    <button class="bg-blue-500 p-2">Edit</button>
                    <button type="button" class="bg-red-500 p-2" @click="$emit('closeModal')">
                        Cancel
                    </button>
                </div>
            </Form>
        </section>
    </Transition>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>
