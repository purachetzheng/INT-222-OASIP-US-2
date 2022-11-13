<script setup>
import { onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import { apiEvent } from '../../../../services/api/lib';
import { getFile } from '../../../../services/api/lib/file';
import { formatDatetime } from '../../../../utils/dateTime';
import { objRenameKeys } from '../../../../utils/ObjectUtils';
const { params } = useRoute()
const router = useRouter()

const eventTemplate = {
    name: '',
    email: '',
    duration: 0,
    startTime: {
        datetime: '',
        date: '',
        time: ''
    },
    notes: '',
    file:{
        id: '',
        name: '',
    },
    category: {
        id: 0,
        name: ''
    }
}
const event = ref({...eventTemplate})

const getEvent = async () => {
    try {
        const { data, status } = await apiEvent.getById(params.eventId)
        const cleanedObj = eventObjectCleaner(data)
        event.value = { ...cleanedObj }
        if(data.fileName) return await getFileName()
    } catch (error) {
        const res = error.response
        event.value = 'no'
        console.log(res.status)
        console.log('error ', error.message)
    }
}
const eventObjectCleaner = (data) => {
    //Rename
    const changesMap = {
        bookingName: 'name',
        bookingEmail: 'email',
        eventNotes: 'notes',
        eventDuration: 'duration',
    }
    objRenameKeys(data, changesMap)
    //Change format
    const { eventCategoryName, eventCategoryId, eventStartTime, fileName, ...updatedObject } = data
    const addedField = {
        category: {
            id: eventCategoryId,
            name: eventCategoryName,
        },
        startTime: {
            datetime: eventStartTime,
            date: formatDatetime.dayMonthYear(eventStartTime),
            time: formatDatetime.hourTime(eventStartTime),
        },
        file: {
            id: fileName
        }
    }
    Object.assign(updatedObject, addedField);
    return updatedObject
}

const getFileName = async () => {
    try {
        const { data, status } = await getFile(event.value.file.id)
        event.value.file.name = data
    } catch (error) {
        const res = error.response
        event.value = 'no'
        console.log(res.status)
        console.log('error ', error.message)
    }
}

onBeforeMount(async () => {
    getEvent()
})

</script>
 
<template>
 <div class="p-8 flex flex-col gap-4">
    <!-- {{params.eventId}} -->
    
    <header class="flex justify-between items-center">
        <p class="text-xl font-semibold">Event Detail</p>
        <div class="flex gap-2">
            <app-button btn-size="sm">Edit</app-button>
            <app-button btn-type="danger" btn-size="sm">Delete</app-button>
        </div>
    </header>
    <div class="">
        <p class="font-medium">Information</p>
        <hr class="my-2 h-0.5 bg-gray-200 border-0 dark:bg-gray-700">
        <div class="flex flex-col gap-2">
            <div class="flex">
                <span class="basis-28 text-gray-500">Name</span>
                <span class="flex-1">{{ event.name }}</span>
            </div>
            <div class="flex">
                <span class="basis-28 text-gray-500">Email</span>
                <span class="flex-1">{{ event.email }}</span>
            </div>
            <div class="flex">
                <span class="basis-28 text-gray-500">Date</span>
                <span class="flex-1">{{ event.startTime.date }}</span>
            </div>
            <div class="flex">
                <span class="basis-28 text-gray-500">Time</span>
                <span class="flex-1">{{ event.startTime.time }}</span>
            </div>
            <div class="flex">
                <span class="basis-28 text-gray-500">Duration</span>
                <span class="flex-1">{{ event.duration }} Minutes</span>
            </div>
        </div>
    </div>
    <div class="">
        <p class="font-medium">Category</p>
        <hr class="my-2 h-0.5 bg-gray-200 border-0 dark:bg-gray-700">
        <div class="flex flex-col gap-2">
            <div class="flex">
                <span class="basis-28 text-gray-500">Name</span>
                <span class="flex-1">{{ event.category.name }}</span>
            </div>
            <div class="flex ">
                <span class="basis-28 text-gray-500">Description</span>
                <span class="flex-1">Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit officia, optio, nam modi nobis distinctio tempora ducimus vero at inventore, architecto suscipit totam officiis?</span>
            </div>
        </div>
    </div>
    <div class="" v-if="event.file.id">
        <p class="font-medium">File</p>
        <hr class="my-2 h-0.5 bg-gray-200 border-0 dark:bg-gray-700">
        <div class="flex flex-col gap-2">
            <div class="flex">
                <span class="basis-28 text-gray-500">Name</span>
                <span class="flex-1">
                    <a :href="`https://intproj21.sit.kmutt.ac.th/us2/api/file/${event.file.id}`" class="underline text-blue-500">{{ event.file.name }}</a>
                </span>
            </div>
            <!-- <div class="flex ">
                <span class="basis-28 text-gray-500">Size</span>
                <span class="flex-1">{{  }} MB.</span>
            </div> -->
        </div>
    </div>
 
 </div>
</template>
 
<style>

</style>