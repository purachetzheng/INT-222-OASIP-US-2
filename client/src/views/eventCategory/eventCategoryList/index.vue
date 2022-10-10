<script setup>
import { onBeforeMount, ref } from 'vue'
import { apiEventCategory } from '../../../services/api/lib'
import CategoryTable from './components/CategoryTable.vue'
import EditCategoryModal from './components/EditCategoryModal.vue'
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

const getEventCategory = async ({} = {}) => {
  try {
    const { data, status } = await apiEventCategory.get()
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
const test = (e) => console.log(e);
</script>

<template>
  <main class="my-container grow flex flex-col py-4 gap-0 justify-between">
    <EditCategoryModal :modal-state="editModal" />
    <CategoryTable class="grow overflow-hidden" :categories="eventCategories" @edit-category="editModal.show" />
    <!-- <div class="bg-white p-2" v-for="category in eventCategories">
      {{ category }}
    </div> -->
  </main>
</template>

<style></style>
