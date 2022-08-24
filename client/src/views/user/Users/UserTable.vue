<script setup>
import { inject, ref } from 'vue'
import { formatDatetime, datetimeCheck } from '../../../utils/dateTime'
import profilePlaceholder from '../../../utils/profilePlaceholder'

defineEmits(['delete-user', 'view-detail'])
const props = defineProps({
  users: {
    type: Array,
    require: true,
  },
})

const viewTime = inject('viewTime')
const roleColor = {
  admin: {
    badges: 'bg-red-200 text-red-500',
    profile: 'bg-red-600 text-white',
  },
  lecturer: {
    badges: 'bg-green-200 text-green-500',
    profile: 'bg-green-600 text-white',
  },
  student: {
    badges: 'bg-blue-200 text-blue-500',
    profile: 'bg-blue-600 text-white',
  },
}
const disPlayUpdated = (datetime) => {
  if (datetimeCheck.isToday(datetime)) return 'Today'
  if (datetimeCheck.isYesterday(datetime)) return 'Yesterday'
  return formatDatetime.dayMonthYear(datetime)
}
</script>

<template>
  <div class="flex flex-col ">
    <header class=" pl-2 pr-4 " v-show="1">
      <ul class="flex flex-wrap font-semibold ">
        <li class="px-4 py-2 basis-80 grow ">User</li>
        <li class="px-4 py-2 basis-28 hidden md:block">Role</li>
        <li class="px-4 py-2 basis-28 hidden md:block">updated</li>
        <li class="px-4 py-2 basis-36 hidden md:block">Actions</li>
      </ul>
    </header>
    <ul class="flex flex-col gap-2 p-2 overflow-y-scroll h-0 grow ">

      <li
        class="bg-white hover:bg-gray-100 flex items-center rounded-md"
        v-for="user in users"  v-show="1"
      >
        <div class="px-4 py-4 flex items-center gap-4 basis-80 grow">
          <div
            class="w-10 h-10 rounded-full flex items-center justify-center"
            :class="roleColor[user.role].profile"
          >
            <p class="font-sans font-semibold">
              {{ profilePlaceholder(user.name) }}
            </p>
          </div>
          <div class="">
            <p class="font-semibold">{{ user.name }}</p>
            <p class="text-sm font-medium">{{ user.email }}</p>
          </div>
        </div>
        <div class="px-4 py-4 basis-28 hidden md:block">
          <span
            class="px-2 py-1 rounded-lg"
            :class="roleColor[user.role].badges"
            >{{ user.role }}</span
          >
        </div>
        <div class="px-4 py-4 basis-28 hidden md:block font-medium">
          {{ disPlayUpdated(user.updatedOn) }}
        </div>
        <div class="px-4 py-4 basis-36 hidden md:block">
          <div class="flex gap-1 items-center">
            <fa-icon
              :icon="['far', 'eye']"
              class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-blue-500 duration-100"
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
        </div>
      </li>
    </ul>
    <!-- <table class="table-auto border-separate border-spacing-y-2 -my-2 w-full">
      <thead class="">
        <tr class="text-left">
          <th scope="col" class="col-head">User</th>
          <th scope="col" class="col-head">Role</th>
          <th scope="col" class="col-head">updated</th>
          <th scope="col" class="col-head">Actions</th>
        </tr>
      </thead>
      <tbody class="overflow-auto h-80">
        <tr
          class="bg-white hover:bg-gray-100 hover:scale-[100.5%]"
          v-for="user in users"
        >
          <td class="col-data">
            <div class="flex items-center gap-4">
              <div
                class="w-10 h-10 rounded-full flex items-center justify-center"
                :class="roleColor[user.role].profile"
              >
                <p class="font-sans font-semibold">
                  {{ profilePlaceholder(user.name) }}
                </p>
              </div>
              <div class="">
                <p class="font-semibold">{{ user.name }}</p>
                <p class="text-sm font-medium">{{ user.email }}</p>
              </div>
            </div>
          </td>
          <td class="col-data">
            <span
              class="px-2 py-1 rounded-lg"
              :class="roleColor[user.role].badges"
              >{{ user.role }}</span
            >
          </td>
          <td class="col-data font-medium">
            {{ disPlayUpdated(user.updatedOn) }}
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
        <tr v-if="users.length === 0">
          No Users
        </tr>
      </tbody>
    </table> -->
  </div>
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
