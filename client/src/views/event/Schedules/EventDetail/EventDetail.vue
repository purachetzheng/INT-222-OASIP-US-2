<script setup>
import { onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import { apiEvent } from '../../../../services/api/lib';
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
        // await getFileName()
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

onBeforeMount(async () => {
    getEvent()
})

</script>
 
<template>
 <div class="p-4">
    {{params.eventId}}
    {{event}}
 </div>
</template>
 
<style>

</style>