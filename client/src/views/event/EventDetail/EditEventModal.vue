<script setup>
import { Field, useForm } from 'vee-validate'
import { onBeforeMount, onUpdated, ref } from 'vue'
import schema from '../../../services/validation/schema/EditEventSchema'
import { datetimeCheck, formatDatetime } from '../../../utils/dateTime';
import FileField from './FileField.vue';
const emits = defineEmits([])
const props = defineProps({
    modalState: {
        type: Object,
        default: {
            // category:{}
        },
    },
})

const formTemplate = {
    note: '',
    datetime: {
        date: '',
        time: ''
    }
}

const { handleSubmit, values, resetForm, meta, setFieldValue,setFieldError, setValues } =
    useForm({
        validationSchema: schema,
        initialValues: { ...formTemplate },
    })

const onSubmit = handleSubmit(({ id, notes, datetime, file }) => {
    // console.log(id, notes, datetime);
    const isFuture = datetimeCheck.isFuture(formatDatetime.jsonDatetime(datetime.date, datetime.time))
    if(!isFuture) {
        return setFieldError('datetime.date', 'Datetime must be future')
    }
    const event = {
        id,
        eventStartTime: formatDatetime.jsonDatetime(datetime.date, datetime.time)
    }
    if (!!notes) event.eventNotes = notes.trim()
    const isNotDefault = file instanceof File
    if(isNotDefault || file == null) {
        event.file = file
    }
    console.log(event);
    props.modalState.onSubmit(event)
})
// const dateInput = ref()
// const timeInput = ref()
const inputFile = ref()
onUpdated(() => {
    if(props.modalState.visible){
        // const event = props.modalState.event
        const {id,  notes, startTime, file } = props.modalState.event
        setValues({
            id: id,
            notes: notes || '',
            datetime: {
                date: formatDatetime.inputDate(startTime.datetime),
                time: formatDatetime.inputTime(startTime.datetime),
            },
        })
        if(file) inputFile.value.setFile({...file, default: 1})
        return
    }
})
// const event = ref({
//   categoryId: props.categoryID,
//   startDate: formatDatetime.dayMonthYear(props.datetime),
//   startTime: formatDatetime.hourTime(props.datetime),
//   inputDate: formatDatetime.inputDate(props.datetime),
//   inputTime: formatDatetime.inputTime(props.datetime),
//   notes: props.notes,
// })
</script>

<template>
    <app-modal :show="modalState.visible">
        <template #header>
            <h1 class="text-2xl font-semibold text-center">Edit Event</h1>
        </template>
        <template #body>
            <div class="w-128">

                <!-- {{ modalState.event }} -->
                <div class="grid grid-cols-2 gap-4">
                    <app-vee-input name="datetime.date" type="date" label="Date" />
                    <app-vee-input name="datetime.time" type="time" label="Time" />
                </div>
            
            <app-vee-textarea
            name="notes"
            :max="500"
            label="Notes"
            
            />
            <FileField ref="inputFile" />
        </div>
            
        </template>
        <template #footer>
            <div class="flex gap-4">
                <app-button btn-type="primary" @click="onSubmit"
                    >Save</app-button
                >
                <app-button btn-type="secondary" @click="modalState.close" outline
                    >Cancel</app-button
                >
            </div>
        </template>
    </app-modal>
</template>

<style></style>
