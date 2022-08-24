<script setup>
import { inject, ref } from 'vue'
import { formatDatetime } from '../../../utils/dateTime'

defineEmits(['delete-user', 'view-detail'])
const props = defineProps({
  users: {
    type: Array,
    require: true,
  },
})

const viewTime = inject('viewTime')
const roleBadges = {
  admin: { class: 'bg-red-300 text-red-500' },
  lecturer: { class: 'bg-green-300 text-green-500' },
  student: { class: 'bg-blue-300 text-blue-500' },
}
</script>

<template>
  <table class="table-auto border-separate border-spacing-y-2 -my-2">
    <thead class="">
      <tr class="text-left">
        <th scope="col" class="col-head">User</th>
        <th scope="col" class="col-head">Role</th>
        <th scope="col" class="col-head" v-show="viewTime">created</th>
        <th scope="col" class="col-head" v-show="viewTime">updated</th>
        <th scope="col" class="col-head">Actions</th>
      </tr>
    </thead>
    <tbody class="">
      <tr class="bg-white" v-for="user in users">
        <td class="col-data">
          <div class="flex items-center gap-4">
            <img
              src="images/default-profile.png"
              alt=""
              class="w-10 h-10 rounded-full"
            />
            <div class="">
              <p class="font-semibold">{{ user.name }}</p>
              <p class="text-sm font-medium">{{ user.email }}</p>
            </div>
          </div>
        </td>
        <td class="col-data">
          <span
            class="px-2 py-1 rounded-lg"
            :class="roleBadges[user.role].class"
            >{{ user.role }}</span
          >
        </td>
        <td class="col-data" v-show="viewTime">
          {{ formatDatetime.dayMonthYear(user.createdOn) }}
        </td>
        <td class="col-data" v-show="viewTime">
          {{ formatDatetime.dayMonthYear(user.updatedOn) }}
        </td>
        <td class="col-data">
          <div class="flex gap-1 items-center">
            <fa-icon
              :icon="['far', 'eye']"
              class="p-2 fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-blue-500 duration-100"
              @click="$emit('view-detail', user)"
            />
            <fa-icon
              :icon="['far', 'pen-to-square']"
              class="fa-lg p-2 cursor-pointer hover:scale-125 text-gray-700 hover:text-purple-500 duration-100"
            />
            <fa-icon
              :icon="['far', 'trash-can']"
              class="fa-lg p-2 cursor-pointer hover:scale-125 text-gray-700 hover:text-red-500 duration-100"
              @click="$emit('delete-user', user.id)"
            />
          </div>
        </td>
      </tr>
      No Users
    </tbody>
  </table>
</template>

<style scoped>
.test {
  @apply bg-blue-400;
}
.col-head {
  @apply px-4 py-4 pb-2 first:pl-18;
}
.col-data {
  @apply px-4 py-4 my-2 first:rounded-l-md last:rounded-r-md;
}
</style>
