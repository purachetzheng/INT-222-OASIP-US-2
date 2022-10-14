<script setup>
import { formatDatetime, datetimeCheck, datetimeCalculate } from '../../../utils/dateTime'
import RoleBadge from '../../../components/user/RoleBadge.vue';
import PlaceholderAvatar from '../../../components/user/PlaceholderAvatar.vue';
defineEmits(['delete-user', 'view-detail'])
const props = defineProps({
  users: {
    type: Array,
    require: true,
  },
})
const disPlayUpdated = (datetime) => {
  // if (datetimeCheck.isHourAgo(datetime)) return datetimeCalculate.timeFromNow(datetime)
  if (datetimeCheck.isToday(datetime)) return datetimeCalculate.timeFromNow(datetime)
  if (datetimeCheck.isYesterday(datetime)) return 'Yesterday'
  return formatDatetime.dayMonthYear(datetime)
}
</script>

<template>
  <div class="flex flex-col ">
    <header class=" pl-2 pr-4 " v-show="1">
      <ul class="flex flex-wrap font-semibold ">
        <li class="px-4 py-2 basis-80 grow ">User</li>
        <li class="px-4 py-2 basis-32 hidden md:block">Role</li>
        <li class="px-4 py-2 basis-44 hidden md:block">updated</li>
        <li class="px-4 py-2 basis-36 hidden md:block">Actions</li>
      </ul>
    </header>
    <ul class="flex flex-col gap-2 p-2 grow ">

      <li
        class="bg-white hover:bg-gray-100 flex items-center rounded-md"
        v-for="user in users"  v-show="1"
      >
        <div class="px-4 py-4 flex items-center gap-4 basis-80 grow">
          <PlaceholderAvatar class="avatar-small" :name="user.name" :role="user.role" />
          <div class="">
            <p class="font-semibold">{{ user.name }}</p>
            <p class="text-sm font-medium">{{ user.email }}</p>
          </div>
        </div>

        <div class="px-4 py-4 basis-32 hidden md:block">
          <RoleBadge :title="user.role" />
        </div>

        <div class="px-4 py-4 basis-44 hidden md:block font-medium">
          {{ disPlayUpdated(user.updatedOn) }}
        </div>
        
        <div class="px-4 py-4 basis-36 hidden md:block">
          <div class="flex gap-3 items-center">
            <fa-icon
              :icon="['far', 'eye']"
              class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-blue-500 duration-100"
              @click="$emit('view-detail', user)"
            />
            <fa-icon
              :icon="['far', 'pen-to-square']"
              class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-purple-500 duration-100"
            />
            <fa-icon
              :icon="['far', 'trash-can']"
              class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-red-500 duration-100"
              @click="$emit('delete-user', user.id)"
            />
          </div>
        </div>
      </li>
    </ul>
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
