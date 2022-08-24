<script setup>
import BaseModal from '../../../../components/base/BaseModal.vue'
import { formatDatetime } from '../../../../utils/dateTime';
defineEmits(['close'])
const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  user: {
    type: Object,
    require: true,
  },
})

</script>

<template>
  <BaseModal :show="show" @close="$emit('closeModal')" :width="'w-160'">
    <template #header>
      <h1 class="text-2xl font-semibold text-center">Edit Event</h1>
    </template>

    <template #body>
        <ul class="">
            <li class="">
                <p class="text-sm">Name:</p>
                <p class="">{{ user.name }}</p>
            </li>
            <li class="">
                <p class="text-sm">Email:</p>
                <p class="">{{ user.email }}</p>
            </li>
            <li class="">
                <p class="text-sm">Role:</p>
                <p class="">{{ user.role }}</p>
            </li>
            <li class="">
                <p class="text-sm">Created Date:</p>
                <p class="">{{ formatDatetime.monthDayYearHourTime(user.createdOn) }}</p>
            </li>
            <li class="">
                <p class="text-sm">Updated Date:</p>
                <p class="">{{ formatDatetime.monthDayYearHourTime(user.updatedOn) }}</p>
            </li>
        </ul>
      
    </template>

    <template #footer>
      <button
        type="button"
        class="bg-red-500 p-2"
        @click="$emit('close')"
      >Close</button>
    </template>
  </BaseModal>
</template>

<style></style>
