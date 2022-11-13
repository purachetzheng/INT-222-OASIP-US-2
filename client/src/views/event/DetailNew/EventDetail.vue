<script setup>
import { defineAsyncComponent, onBeforeMount, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { apiEvent } from '../../../services/api/lib'
import { getFile } from '../../../services/api/lib/file'
import { isFuture, formatDatetime } from '../../../utils/dateTime'
import PageLoader from '../../../components/shared/Loading/PageLoader.vue'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'
import { objRenameKeys } from '../../../utils/ObjectUtils'

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
        await getFileName()
    } catch (error) {
        const res = error.response
        event.value = 'no'
        console.log(res.status)
        console.log('error ', error.message)
    }
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
        const res = await apiEvent.patch(params.eventId, eventData)
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
        const res = await apiEvent.delete(params.eventId)
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

const EditEventModal = defineAsyncComponent(async () => {
    await new Promise((resolve) => setTimeout(resolve, 1000))
    return import('./EditEventModal.vue')
})
</script>

<template>
    <PageWrapper>
        <div class="">
            <h1 class="">{{event.name}}</h1>

            <!-- <p class="">name: {{event.name}}</p>
            <p class="">email: {{event.email}}</p>
            <p class="">category: {{event.category.name}}</p>
            <p class="">startDate: {{event.startTime.date}}</p>
            <p class="">startTime: {{event.startTime.time}}</p>
            <p class="">duration: {{event.duration}} min</p>
            <p class="">notes: {{event.notes}}</p>
            <p class="">file: <a :href="`api/file/${event.file.id}`" class="text-blue-500" download>{{ event.file.name}}</a></p> -->
        </div>
    </PageWrapper>
    
</template>

<style></style>
