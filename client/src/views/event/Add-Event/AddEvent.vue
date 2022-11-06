<script setup>
import { computed, onBeforeMount, provide, reactive, ref } from 'vue'
import { apiEvent, apiEventCategory } from '../../../services/api/lib'
import { formatDatetime } from '../../../utils/dateTime'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'
import { useUserStore } from '../../../stores'
import { postGuestsEvent } from '../../../services/api/lib/guests'
import SelectCategory from './stages/SelectCategory.vue'
import FillForm from './stages/FillForm.vue'
import TabPage from './components/TabStep.vue'
import TabStep from './components/TabStep.vue'
const userStore = useUserStore()
// const step = ref(1)
const addEventForm = reactive({
  step: 1,
  selectedCategoryId: 0,
  setSelectedCategoryId(id) {
    return (addEventForm.selectedCategoryId = id)
  },
  selectedCategory: computed(() => categories.value.find((c) => c.id == addEventForm.selectedCategoryId)),
  nextStep() {
    addEventForm.step++
  },
  prevStep() {
    addEventForm.step--
  },
  setStep(step) {
    addEventForm.step = step
  },
  isStepComplete: computed(() => {
    if (addEventForm.step === 1) return addEventForm.selectedCategoryId !== 0
    // return true
    // if(addEventForm.step === 2) return selectedCategoryId !== 0
    // if(addEventForm.step === 1) return selectedCategoryId !== 0
  }),
})

const stepInfo = [
  {
    id: 1,
    icon: ['fas', 'tags'],
    name: 'Choose Category',
    desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugiat dolorum reiciendis, amet.',
  },
  {
    id: 2,
    icon: ['fas', 'tags'],
    name: 'Fill Information',
    desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugiat dolorum reiciendis, amet.',
  },
  {
    id: 3,
    icon: ['fas', 'tags'],
    name: 'Successfully',
    desc: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugiat dolorum reiciendis, amet.',
  },
]

const categories = ref([])

const selectedCategoryId = ref(0)

const selectedCategory = computed(() =>
  categories.value.find((c) => c.id == addEventForm.selectedCategoryId)
)

provide('selectedCategoryId', selectedCategoryId)
provide('selectedCategory', selectedCategory)

const getCategories = async () => {
  try {
    const { data } = await apiEventCategory.get()
    categories.value = data.content
    // categories.value = data
    console.log(data)
  } catch (error) {
    console.log(error)
  }
}

onBeforeMount(() => {
  getCategories()
})

const onSubmit = async ({ name, email, datetime, notes }) => {
  const newEvent = {
    bookingName: name,
    bookingEmail: email,
    eventCategoryId: selectedCategory.value.id,
    eventDuration: selectedCategory.value.eventDuration,
    eventStartTime: formatDatetime.jsonDatetime(datetime.date, datetime.time),
  }
  if (notes) newEvent.eventNotes = notes
  console.log(newEvent)
  try {
    const { data } =  userStore.isAuth ? await apiEvent.post(newEvent) : await postGuestsEvent(newEvent)
    console.log(data)
    alert('ok')
  } catch (error) {
    console.log(error.message)
  }
}


</script>

<template>
  <PageWrapper>
    <div class="min-h-full flex flex-col">
      <header class="flex justify-between items-center">
        <div class="">
          <h1 class="text-2xl font-semibold">
            Book Event
          </h1>
        </div>
      </header>
      <div class="flex gap-8 mt-4">
        <div class="bg-white rounded-lg shadow-lg p-4 h-fit">
          <TabStep :step-now="addEventForm.step"
          :step-info="stepInfo"
          @set-step="addEventForm.setStep" /> 
          {{addEventForm.selectedCategoryName}}
        </div>
        <div class="bg-white rounded-lg shadow-lg p-4">
          <h1 class="text-xl font-semibold mb-4">{{ stepInfo[addEventForm.step - 1].name }}</h1>
          <SelectCategory
            :categories="categories"
            :add-event-form="addEventForm"
            v-if="addEventForm.step === 1"
          />
          <FillForm v-if="addEventForm.step === 2" :add-event-form="addEventForm" @submit-form="onSubmit" />
          <div class="flex justify-between flex-row-reverse gap-4 mt-4" v-show="addEventForm.step != 2">
            <app-button
              button-type="primary"
              :disabled="!addEventForm.isStepComplete"
              @click="addEventForm.nextStep"
              >Next</app-button
            >
            <app-button
              button-type="no-border"
              v-if="addEventForm.step > 1"
              @click="addEventForm.prevStep"
              :button-icon="['fas', 'arrow-right']"
              >Back</app-button
            >
            
          </div>
        </div>
      </div>
    </div>
  </PageWrapper>
</template>

<style></style>
