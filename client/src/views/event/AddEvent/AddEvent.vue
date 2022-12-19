<script setup>
import { computed, onBeforeMount, reactive, ref, onUpdated, watch } from 'vue'
import { apiEvent, apiEventCategory } from '../../../services/api/lib'
import { serialize } from 'object-to-formdata'
import { formatDatetime } from '../../../utils/dateTime'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'
import BookingStepTemplate from './BookingStepTemplate.vue'
import EventCategoryCard from './EventCategoryCard.vue'
import { useUserStore } from '../../../stores'
import { objRemoveEmpty, objRenameKeys } from '../../../utils/ObjectUtils'

import schema from '@/services/validation/schema/AddEventSchema'
import { useForm } from 'vee-validate'
import FillFormFileField from './FillFormFileField.vue'
const userStore = useUserStore()

const { handleSubmit, resetForm, setValues, setFieldValue, meta: infoFormMeta } = useForm({
    validationSchema: schema,
    initialValues: {
        name: '',
        email: '',
        note: '',
    },
})

const completedSteps = computed(() => {
    let x = 0

    if (!Boolean(selectedCategory.value.id)) return x
    x++

    return x
})
const bookingForm = reactive({
    step: 1,
    completedSteps: completedSteps,
    nextStep() {
        bookingForm.step++
    },
    prevStep() {
        bookingForm.step--
    },
    setStep(step) {
        if (bookingForm.step == step) return (bookingForm.step = 0)
        bookingForm.step = step
    },
})
const selectedCategoryTemplate = { id: 0 }
const selectedCategory = ref({ ...selectedCategoryTemplate })
// const isStepComplete = computed(() => {
//     if ((bookingForm.step = 1)) {
//         return Boolean(chooseCategoryStep.selected)
//     }
// })

const fillInformationStep = reactive({
    THIS_STEP: 2,
})
// const isFillInformationStepComplete = computed(() => meta.valid)
const chooseCategoryStep = reactive({
    THIS_STEP: 1,
    categories: [],
    selected: selectedCategory,
    async getCategories() {
        try {
            const { data } = await apiEventCategory.get()
            chooseCategoryStep.categories = data.content
            console.log(data)
        } catch (error) {
            console.log(error)
        }
    },
    select(category){
        chooseCategoryStep.selected = category
        resetForm()
        setFieldValue('email', userStore.user.email);
        setFieldValue('name', userStore.user.name);
    },
    nextStep() {
        bookingForm.setStep(chooseCategoryStep.THIS_STEP + 1)
    },
})
const isChooseCategoryStepComplete = computed(() => Boolean(chooseCategoryStep.selected.id))
// const categories = ref([])
// const getCategories = async () => {
//     try {
//         const { data } = await apiEventCategory.get()
//         categories.value = data.content
//         // categories.value = data
//         console.log(data)
//     } catch (error) {
//         console.log(error)
//     }
// }
onBeforeMount(() => {
    // getCategories()
    chooseCategoryStep.getCategories()
})

const onSubmit =handleSubmit( async ({ name, email, datetime, notes, file }) => {
    const newEvent = {
        eventCategoryId: selectedCategory.value.id,
        eventDuration: selectedCategory.value.eventDuration,
        eventStartTime: formatDatetime.jsonDatetime(datetime.date, datetime.time),
        eventNotes: notes,
        name,
        email,
        file,
    }
    if (!notes) delete newEvent['notes']
    if (!file) delete newEvent['file']
    const changesMap = {
        name: 'bookingName',
        email: 'bookingEmail',
    }
    objRenameKeys(newEvent, changesMap)
    console.log(newEvent)
    const newEventFormData = serialize(newEvent)
    console.log(newEventFormData)
    try {
        const { data } = userStore.isAuth
            ? await apiEvent.post(newEvent)
            : await postGuestsEvent(newEvent)
        console.log(data)
        alert('ok')
    } catch (error) {
        console.log(error.message)
    }
})

</script>

<template>
    <PageWrapper pageTitle="Book Event">
        <!-- <p class="">step: {{ bookingForm.step }}</p>
        <p class="">completed steps: {{ bookingForm.completedSteps }}</p>
        <p class="">
            selectedCategory: {{ selectedCategory.eventCategoryName }}#{{ selectedCategory.id }}
        </p> -->

        <div class="flex flex-col gap-1">
            <BookingStepTemplate
                :thisStep="1"
                :formStep="bookingForm.step"
                :completedSteps="bookingForm.completedSteps"
                :stepComplete="isChooseCategoryStepComplete"
                @next="chooseCategoryStep.nextStep"
                @selectStep="bookingForm.setStep"
            >
                <ul
                    class="h-full auto-rows-min grid xl:grid-cols-3 lg:grid-cols-2 md:grid-cols-1 grid-cols-1 gap-4"
                >
                    <EventCategoryCard
                        v-for="category in chooseCategoryStep.categories"
                        :category="category"
                        :selected="chooseCategoryStep.selected.id === category.id"
                        @click="chooseCategoryStep.select(category)"
                    />
                </ul>
            </BookingStepTemplate>

            <BookingStepTemplate
                :thisStep="2"
                :formStep="bookingForm.step"
                :completedSteps="bookingForm.completedSteps"
                :stepComplete="infoFormMeta.valid"
                @selectStep="bookingForm.setStep"
                @next="onSubmit"
            >
                <template #step-title>Fill Information</template>
                <template #step-desc>Lorem ipsum dolor sit amet consectetur adipisicing.</template>
                <template #next-button-name>Book</template>
                <!-- {{infoFormMeta.valid}} -->
                <div class="mx-auto max-w-2xl">
                    <div class="col-span-2">
                        <div class="">
                            <label for="category" class="text-sm font-medium leading-3"
                                >Category</label
                            >
                        </div>
                        <input
                            class="form-input h-10 mb-4"
                            name="category"
                            :value="selectedCategory.eventCategoryName"
                            type="text"
                            disabled
                        />
                    </div>
                    <app-vee-input name="name" type="text" :max="100" label="Name" />
                    <app-vee-input name="email" type="text" :max="100" label="Email" />
                    
                    <div class="grid grid-cols-2 gap-4"></div>
                    <div class="grid grid-cols-2 gap-4">
                        <app-vee-input name="datetime.date" type="date" label="Date" />
                        <app-vee-input name="datetime.time" type="time" label="Time" />
                    </div>
                    <FillFormFileField />
                    <app-vee-textarea name="notes" :max="500" label="Notes" />
                </div>
            </BookingStepTemplate>
        </div>
        <!-- <div class="min-h-full flex flex-col"></div> -->
    </PageWrapper>
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>
