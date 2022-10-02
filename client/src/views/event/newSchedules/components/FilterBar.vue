<script setup>
import { computed, onBeforeMount, reactive, ref } from 'vue'
import { apiEventCategory } from '../../../../services/api/lib'
import BaseInputDate from '../../../../components/base/BaseInput/BaseInputDate.vue'
import BaseDropDown from './BaseDropDown.vue'
const emits = defineEmits(['filter-event'])
// const prop = defineProps({
//         first:{
//         type:String,
//         require:true,
//    },

// })

const filterSetting = reactive({
  eventCategoryId: null,
  dateStatus: 'all',
  date: null,
})
const categories = ref([])
const getCategories = async () => {
  try {
    const { data } = await apiEventCategory.get()
    categories.value = data.content
    // console.log(data)
  } catch (error) {
    console.log(error)
  }
}
const test = ref(null)
onBeforeMount(() => {
  getCategories()
})

const select = (value, field) => {
  filterSetting[field] = value
  emits('filter-event', filterSetting)
}
</script>

<template>
  <div class="">
    <div class="flex gap-2 justify-between">
      <!-- <button :id="id" class="block w-full px-2 h-9 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500">
        All
      
        </button> -->
      <!-- <button
        class="block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
      >
        <span class="flex gap-4 items-center px-3">
          <span class="">All</span>
          <fa-icon :icon="['fas', 'chevron-down']" class="fa-2xs" />
        </span>
      </button> -->
      <button
        class="block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
      >
        <span class="flex gap-2 items-center px-3">
          <fa-icon :icon="['fas', 'sliders']" class="fa-" />
          <span class="">Filters</span>
        </span>
      </button>
      <div ref="test" class="overflow-x-hidden">
        <ul class="flex gap-2 whitespace-nowrap">
          <li class="bg-gray-100 rounded-md" v-for="category in categories">
            <button
              class="h-10 px-3"
              @click="select(category.id, 'eventCategoryId')"
            >
              {{ category.eventCategoryName }}
            </button>
          </li>
        </ul>
      </div>
      <div class="">d</div>
    </div>
    <div class="pt-4 flex gap-4">
      <BaseDropDown
        class="box-s w-40"
        field="dateStatus"
        default="All Events"
        :options="[{name:'All Events', value: 'all'}, {name:'Past', value: 'past'}, {name:'Upcoming', value: 'upcoming'}]"
        @select="select"
      />
      <!-- <input
        type="date"
        class="px-3 block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
      /> -->
      <input
        type="date"
        class="px-3 block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
        @change="select($event.target.value, 'date')"
      />
      {{ filterSetting }}
    </div>
  </div>
</template>

<style></style>
