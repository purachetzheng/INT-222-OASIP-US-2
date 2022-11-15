<script setup>
import { onBeforeMount, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import { apiEvent } from '../../../services/api/lib';
import { getFile } from '../../../services/api/lib/file';
import { formatDatetime } from '../../../utils/dateTime';
import { objRenameKeys } from '../../../utils/ObjectUtils';
import EditEventModal from '../EventDetail/EditEventModal.vue';
const { params } = useRoute()
const router = useRouter()
const emits = defineEmits(['cancel-event', 'submit-edit'])
const props = defineProps({
  closeSlideOver: {
    type: Function,
    default: () => {}
  },
  slideOverStage: {
    type: Object,
    default: {}
  },
})
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
        id: 0,
        name: '',
        size: ''
    },
    category: {
        id: 0,
        name: '',
        desc: ''
    }
}
const event = ref({...eventTemplate})

const editingEventTemplate = {
  id: null,
  eventCategoryName: '',
  eventCategoryDescription: '',
  eventDuration: null,
}

const editModal = reactive({
  visible: false,
  event: { ...eventTemplate }, 
  show: () => {
    editModal.event = event.value
    editModal.visible = true
  },
  close: () => {
    editModal.visible = false
  },
  onSubmit: (event) => {
    emits('submit-edit', event)
  }
})

const getEvent = async () => {
    try {
        const { data, status } = await apiEvent.getById(params.eventId)
        const cleanedObj = eventObjectCleaner(data)
        event.value = { ...cleanedObj }
        // if(data.fileName) return await getFileName()
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
    const fileChangesMap = {
        fileName: 'name',
        fileSize: 'size',
    }
    if(data.file) objRenameKeys(data.file, fileChangesMap)
    
    objRenameKeys(data, changesMap)
    //Change format
    const { eventcategory: eventCategory, eventStartTime, ...updatedObject } = data
    const addedField = {
        category: {
            id: eventCategory.id,
            name: eventCategory.eventCategoryName,
            desc: eventCategory.eventCategoryDescription
        },
        startTime: {
            raw: eventStartTime,
            datetime: formatDatetime.full2(eventStartTime),
            date: formatDatetime.dayMonthYear(eventStartTime),
            time: formatDatetime.hourTime(eventStartTime),
        },
    }
    // console.log(file);
    
    Object.assign(updatedObject, addedField);
    return updatedObject
}

// const getFileName = async () => {
//     try {
//         const { data, status } = await getFile(event.value.file.id)
//         event.value.file.name = data
//     } catch (error) {
//         const res = error.response
//         console.log(res.status)
//         console.log('error ', error.message)
//     }
// }

const cancelEvent = async () => {
    props.slideOverStage.close()
    // props.closeSlideOver()
//   try {
//     const res = await apiEvent.delete(params.eventId)
//     console.log(res.data)
//     alert('Cancel successfully')
//   } catch (error) {
//     console.log(error.message)
//     const { data, status } = error.response
//     alert(data.message)
//   }
}


onBeforeMount(async () => {
    getEvent()
})
const editingMode = reactive({
    state: false,
    on: () => {
        editingMode.state = true
    },
    off: () => editingMode.state = false
})


</script>
 
<template>
 <div class="p-8 flex flex-col gap-4 overflow-y-auto">
    <!-- {{params.eventId}} -->
    <EditEventModal :modal-state="editModal"  />
    <header class="flex justify-between items-center">
        <p class="text-xl font-semibold">Event Detail </p>
        <div class="flex gap-2">
            <app-button btn-size="sm" @click="editModal.show">Edit</app-button>
            <app-button btn-type="danger" btn-size="sm" @click="$emit('cancel-event', params.eventId)">Cancel</app-button>
        </div>
    </header>
    
    <div class="font-medium">
        <p class="">Information</p>
        <hr class="my-2 h-0.5 bg-gray-200 border-0 dark:bg-gray-700">
        <div class="flex flex-col gap-2 text-sm">
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
            <div class="flex" v-if="event.file">
                <span class="basis-28 text-gray-500">File</span>
                <span class="flex-1">
                    <a :href="`https://intproj21.sit.kmutt.ac.th/us2/api/file/${event.file.id}`" 
                        class="underline text-blue-500"
                    >
                        {{ event.file.name }}
                    </a>
                </span>
            </div>
        </div>
    </div>

    <div class="font-medium">
        <p class="">Category</p>
        <hr class="my-2 h-0.5 bg-gray-200 border-0 dark:bg-gray-700">
        <div class="flex flex-col gap-2 text-sm">
            <div class="flex">
                <span class="basis-28 text-gray-500">Name</span>
                <span class="flex-1">{{ event.category.name }}</span>
            </div>
            <div class="flex">
                <span class="basis-28 text-gray-500">Description</span>
                <span class="flex-1">{{event.category.desc}}</span>
            </div>
        </div>
    </div>
    <div class="font-medium">
        <p class="">Note</p>
        <hr class="my-2 h-0.5 bg-gray-200 border-0 dark:bg-gray-700">
        <div class="text-sm">{{ event.notes }}</div>
    </div>
 </div>
</template>
 
<style>

</style>