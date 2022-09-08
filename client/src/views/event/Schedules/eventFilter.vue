<script setup>
import { BaseDropdown, BaseInputDate } from '../../../components/base';
import { IcSortRound } from '../../../assets/icons/editing-icons';
import { apiEventCategory } from '../../../services/api/lib';
import { computed, onBeforeMount, ref } from 'vue';
defineEmits([])
const props = defineProps({
    first: {
        type: String,
        require: true,
    },
})
const categories = ref([])

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
const categoriesOption = computed(()=>{
    const options = []
    categories.value.forEach(c => options.push(c.eventCategoryName)) 
    return options
})
onBeforeMount(() => {
  getCategories()
})

const test = (e) => console.log(e);
</script>
 
<template>
    <div class="flex gap-2 justify-center">
        <IcSortRound class="w-8 h-8 mr-4" />
        <div class="w-60">
            <BaseDropdown name="category" selected="All Category" :options="categoriesOption"
                @select-category="test" />
        </div>
        <div class="w-40">
            <BaseDropdown name="event-status" selected="All Events" :options="['Past', 'Upcoming']"
                @select-category="test" />
        </div>
        <div class="w-56">
            <BaseInputDate name="event-date" @pick-date="test" />
        </div>
    </div>
</template>
 
<style>
</style>