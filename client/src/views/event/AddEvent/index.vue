<script setup>
import { computed, onBeforeMount, provide, reactive, ref } from 'vue'
import ProgressBar from './ProgressBar.vue'
import SelectCategory from './stages/SelectCategory.vue'
import FillForm from './stages/FillForm.vue'
import { apiEvent, apiEventCategory } from '../../../services/api/lib'
import { formatDatetime } from '../../../utils/dateTime'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'

// const step = ref(1)
const addEvent = reactive({
  step: 1,
  nextStep() {
    addEvent.step++
  },
  prevStep() {
    addEvent.step--
  },
})

const categories = ref([])

const selectedCategoryId = ref(0)

// const getCateById = () => {
//   const c = categories.value
//   return
// }
const selectedCategory = computed(() =>
  categories.value.find((c) => c.id == selectedCategoryId.value)
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
    eventCategoryId: selectedCategoryId.value,
    eventDuration: selectedCategory.value.eventDuration,
    eventStartTime: formatDatetime.jsonDatetime(datetime.date, datetime.time),
  }
  if (notes) newEvent.eventNotes = notes
  console.log(newEvent)
  try {
    const { data } = await apiEvent.post(newEvent)
    console.log(data)
    alert('ok')
  } catch (error) {
    console.log(error.message)
  }
}
</script>

<template>
  <PageWrapper>
    <div class="grow flex flex-col gap-4 justify-between">
      <ProgressBar :step="addEvent.step" />
      <SelectCategory
        v-if="addEvent.step === 1"
        class="grow"
        :categories="categories"
        @next-step="addEvent.nextStep()"
      />
      <FillForm v-if="addEvent.step > 1" class="grow" @submit-form="onSubmit" />
    </div>
  </PageWrapper>
</template>

<style></style>
