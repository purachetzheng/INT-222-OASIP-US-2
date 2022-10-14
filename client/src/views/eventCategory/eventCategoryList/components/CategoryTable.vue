<script setup>
import { computed } from 'vue';
defineEmits(['delete-category', 'view-detail', 'edit-category'])
const props = defineProps({
  categories: {
    type: Array,
    require: true,
  },
})

// const category = computed(() => {
//   return {
//     id: props.category.id,
//     name: props.category.eventCategoryName,
//     desc: props.category.eventCategoryDescription,
//     duration: props.category.eventDuration,
//   }
// })
</script>

<template>
  <div class="flex flex-col h-full">
    <header class=" pl-2 pr-4 " v-show="1">
      <ul class="flex flex-wrap font-semibold ">
        <li class="px-4 py-2 basis-60 ">Name</li>
        <li class="px-4 py-2 basis-80 hidden md:block grow">Description</li>
        <li class="px-4 py-2 basis-28 hidden md:block">Duration</li>
        <li class="px-4 py-2 basis-24 hidden md:block">Actions</li>
      </ul>
    </header>
    <ul class="flex flex-col gap-2 p-2">

      <li
        class="bg-white hover:bg-gray-100 flex items-center rounded-md"
        v-for="category in categories"  v-show="1"
      >
        <div class="px-4 py-4 flex items-center gap-4 basis-60 ">
          <!-- <PlaceholderAvatar class="avatar-small" :name="user.name" :role="user.role" />
          <div class="">
            <p class="font-semibold">{{ user.name }}</p>
            <p class="text-sm font-medium">{{ user.email }}</p>
          </div> -->
          {{ category.eventCategoryName}}
        </div>
        
        <div class="px-4 py-4 basis-80 hidden md:block grow">
          <p class="text-sm">
            {{ category.eventCategoryDescription}}
          </p>
          <!-- <RoleBadge :title="user.role" /> -->
        </div>
        
        <div class="px-4 py-4 basis-28 hidden md:block font-medium">
          <p class="text-sm">
            {{ category.eventDuration}} Minutes
          </p>
          <!-- {{ disPlayUpdated(user.updatedOn) }} -->
        </div>
        
        <div class="px-4 py-4 basis-24 hidden md:block">
          <div class="flex gap-3 items-center">
            <fa-icon
              :icon="['far', 'pen-to-square']"
              class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-purple-500 duration-100"
              @click="$emit('edit-category',category)"
            />
            <!-- <fa-icon
              :icon="['far', 'trash-can']"
              class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-red-500 duration-100"
              @click="$emit('delete-category', category.id)"
            /> -->
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
</style>
