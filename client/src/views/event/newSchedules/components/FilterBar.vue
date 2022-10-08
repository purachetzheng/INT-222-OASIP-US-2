<script setup>
import { computed, onBeforeMount, reactive, ref } from 'vue'
import { apiEventCategory } from '../../../../services/api/lib'
import BaseInputDate from '../../../../components/base/BaseInput/BaseInputDate.vue'
import BaseDropDown from './BaseDropDown.vue'
// const emits = defineEmits(['filter-event'])
const props = defineProps({
  filterSetting: {
    type: Object,
    require: true,
  },
})
const {filterSetting} = props


//ในอนาคต ใช้ v-model แทน
const setCategoryId = (id) => {
  filterSetting.eventCategoryId = id
  // filterProxy.eventCategoryId = id
}
const setDateStatus = (status) => {
  filterSetting.dateStatus = status
  // filterProxy.dateStatus = status
}
const setDate = (date) => {
  filterSetting.date = date
  // filterProxy.date = date
}


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

const subFilterBar = reactive({
  state: false,
  isOnDone: false,
  show: () => (subFilterBar.state = true),
  close: () => (subFilterBar.state = false),
  toggle: () => {
    subFilterBar.state = !subFilterBar.state
    subFilterBar.isOnDone = true
    setTimeout(() => (subFilterBar.isOnDone = false), 300)
  },
})
</script>

<template>
  <div class=" ">
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
      
      <div ref="test" class="overflow-x-hidden">
        <ul class="flex gap-2 whitespace-nowrap">
          <li
            class="rounded-md duration-100 hover:bg-gray-50"
            :class="[
              filterSetting.eventCategoryId === category.id && 'bg-gray-50',
            ]"
            v-for="category in categories"
          >
            <button class="h-10 px-3" @click="setCategoryId(category.id)">
              {{ category.eventCategoryName }}
            </button>
          </li>
        </ul>
      </div>
      <button
        class="block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
        @click="subFilterBar.toggle"
      >
        <span class="flex gap-2 items-center px-3">
          <fa-icon :icon="['fas', 'sliders']" class="fa-" />
          <span class="">Filters</span>
        </span>
      </button>
    </div>
    <div
      class="flex gap-4 subFilter"
      :class="[
        subFilterBar.state ? 'subFilterOn' : 'subFilterOff',
        !subFilterBar.state || subFilterBar.isOnDone ? 'overflow-hidden' : '',
      ]"
    >
      <BaseDropDown
        class="box-s w-40"
        field="dateStatus"
        default="All Events"
        :options="[
          { name: 'All Events', value: 'all' },
          { name: 'Past', value: 'past' },
          { name: 'Upcoming', value: 'upcoming' },
        ]"
        @select="setDateStatus"
      />
      <!-- <input
            type="date"
            class="px-3 block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
          /> -->
      <input
        type="date"
        class="px-3 block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
        @change="setDate($event.target.value)"
      />
      {{ filterSetting }}
    </div>
  </div>
</template>

<style scoped>
.subFilter {
  @apply duration-200 ease-in-out;
}
.subFilterOn {
  @apply mt-4 h-10;
}
.subFilterOff {
  @apply h-0;
}
.animation {
  position: relative;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: max-width 0.2s ease-in-out;
  transition: max-width 0.2s ease-in-out;
}
</style>
