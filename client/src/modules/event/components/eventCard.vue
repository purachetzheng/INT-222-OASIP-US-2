<script setup>
import { computed } from 'vue'
import {
    formatMonthDayYear,
    formatHourTime,
    formatFull1,
    formatFull2,
} from '../utils/dateTime'
import { IcClock, IcClockOutline } from '../../../assets/icons/time-icons'
import { IcArrowRight, IcArrowRightBold } from '../../../assets/icons/arrows-icons'

defineEmits(['clickEventCard'])
const props = defineProps({
    event: {
        type: Object,
        require: true,
    },
})
const event = computed(() => {
    return {
        id: props.event.id,
        name: props.event.bookingName,
        category: {
            id: props.event.eventCategoryID,
            name: props.event.eventCategoryName,
        },
        duration: props.event.eventDuration,
        startDate: formatMonthDayYear(props.event.eventStartTime),
        startTime: formatHourTime(props.event.eventStartTime),
    }
})
</script>

<template>
    <li class="flex flex-col overflow-hidden shadow-md bg-white  font-semibold rounded-md cursor-pointer group"
        @click="$emit('clickEventCard')">
        <div class="h-8 w-full bg-blue-500 mt-0 group-hover:-mt-8 duration-300 ease-in-out"></div>
        <div class="relative bg-white p-4 flex flex-col gap-0.5">
            <span class="text-sm text-blue-500">{{ event.startDate }}</span>
            <h1 class="text-xl pb-1 font-bold">{{ event.name }}</h1>
            <h2 class="text-md text-gray-800">{{ event.category.name }}</h2>
            <div class="flex items-center gap-2">
                <IcClockOutline class="text-gray-700" />
                <p class="text-sm text-gray-700">
                    {{ event.startTime }} ({{ event.duration }} min)
                </p>
            </div>
            <p class="absolute w-full -bottom-8 left-0 h-8 bg-gradient-to-r from-green-400 to-blue-500 flex items-center justify-center gap-1 text-white">
                View Detail <span class=""><IcArrowRightBold /></span>
            </p>
        </div>

    </li>
</template>

<style>
</style>
