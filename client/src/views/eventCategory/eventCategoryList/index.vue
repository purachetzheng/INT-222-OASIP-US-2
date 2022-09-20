<script setup>
import { onBeforeMount, ref } from 'vue'
import { apiEventCategory } from '../../../services/api/lib'

const eventCategories = ref([])
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
</script>

<template>
  <main
    class="my-container h-full flex flex-col py-4 gap-4 justify-between test"
  >
    <div class="bg-white p-2" v-for="category in eventCategories">
      {{ category }}
    </div>
  </main>
</template>

<style></style>
