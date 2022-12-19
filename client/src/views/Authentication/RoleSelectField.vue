<script setup>
import { useField } from 'vee-validate'
import { toRef, computed } from 'vue'

defineEmits([])
const props = defineProps({
  // name: {
  //   type: String,
  //   require: true,
  // },
  // type: {
  //   type: String,
  //   require: true,
  // },
  label: {
    type: String,
    default: 'Role'
  },
  // attribute: {
  //   type: Object,
  //   default: {},
  // },
})

const name = 'role'
const { value, errorMessage, handleChange } = useField(name, () => {}, {
  validateOnValueUpdate: false,
  validateOnMount: false,
})
const roles = ['admin', 'lecturer', 'student']
</script>

<template>
  <div class="relative flex flex-col py-1">
    <p class="text-sm font-medium leading-3 pb-1 ">{{label}}</p>
    <ul class="grid grid-cols-2 gap-2">
      <li v-for="role in roles">
        <input
          type="radio"
          :id="role"
          :name="name"
          :value="role"
          v-model="value"
          class="peer hidden"
        />
        <label
          :for="role"
          class="card card-checked dark-card dark-card-checked"
          :class="[errorMessage && 'card-error']"
        >
          <fa-icon :icon="['fas', 'circle']" class="fa-lg " />
          <h5 class="text-lg font-semibold leading-4 capitalize">{{role}}</h5>
        </label>
      </li>
    </ul>
    <span
      v-show="errorMessage"
      class="absolute right-1 -bottom-5 text-red-500 text-sm"
      >{{ errorMessage }}</span>
  </div>
</template>

<style scoped>
.card {
  @apply flex flex-col sm:flex-row gap-4 items-center p-4 text-gray-500 bg-white rounded-lg border-2 border-gray-200 cursor-pointer hover:bg-gray-100 hover:text-gray-600;
}
.card-checked {
  @apply peer-checked:border-blue-600 peer-checked:text-blue-600;
}
.dark-card {
  @apply dark:text-gray-400 dark:bg-gray-800 dark:border-gray-700 dark:hover:text-gray-300 dark:hover:bg-gray-700;
}
.dark-card-checked {
  @apply dark:peer-checked:text-blue-500;
}
.card-error{
  @apply border-red-500
}
</style>
