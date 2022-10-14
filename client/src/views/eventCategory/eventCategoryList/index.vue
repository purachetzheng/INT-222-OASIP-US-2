<script setup>
import { onBeforeMount, ref } from 'vue'
import { apiGetEventCategory, apiPatchEventCategory } from '../../../services/api/lib/eventCategory'
import CategoryTable from './components/CategoryTable.vue'
import EditCategoryModal from './components/EditCategoryModal.vue'
import PageWrapper from '../../../components/Layout/PageWrapper.vue';
const isLoading = ref(false)
const eventCategories = ref([])

const editingCategoryTemplate = {
  id: null,
  eventCategoryName: '',
  eventCategoryDescription: '',
  eventDuration: null,
}

const editModal = ref({
  visible: false,
  category: { ...editingCategoryTemplate }, 
  show: (category) => {
    editModal.value.category = category
    editModal.value.visible = true
  },
  close: () => {
    editModal.value.visible = false
  },
})

const patchCategory = async (submitCategory) =>{
  const {id, ...category} = submitCategory
  try{
    const { data, status } = await apiPatchEventCategory({id, data: category})
    eventCategories.value = eventCategories.value.map((c) => c.id === data.id ? data: c)
    console.log(data);
  }catch(error){
    const res = error.response
    console.log('error ', error.message)
  }
}

const getEventCategory = async () => {
  try {
    const { data, status } = await apiGetEventCategory()
    console.log(data)
    const { content, number, totalPages } = data
    eventCategories.value = content
    return data.content
  } catch (error) {
    // console.log(error)
    // console.log(error.response)
    const res = error.response
    console.log(res.status)
    console.log('error ', error.message)
  }
}
onBeforeMount(async () => {
  getEventCategory()
})
</script>

<template>
  <PageWrapper>
  <main class="flex flex-col gap-0 justify-between">
    <EditCategoryModal :modal-state="editModal" @submit-edit-form="patchCategory" />
    <CategoryTable class="" :categories="eventCategories" @edit-category="editModal.show" />
    <!-- <div class="bg-white p-2" v-for="category in eventCategories">
      {{ category }}
    </div> -->
  </main>
</PageWrapper>
</template>

<style></style>
