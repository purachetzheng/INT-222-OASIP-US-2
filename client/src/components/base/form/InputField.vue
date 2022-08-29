<script setup>
import { useField } from 'vee-validate'
import { toRef, computed } from 'vue'

defineEmits([])
const props = defineProps({
  name: {
    type: String,
    require: true,
  },
  type: {
    type: String,
    require: true,
  },
  label: {
    type: String,
    default: '',
  },
  required:{
    type: Boolean,
    default: false
  },
  max: {
    type: Number,
    default: 999,
  },
  attr: {
    type: Object,
    default: {},
  },
})

const nameRef = toRef(props, 'name')
const { value, errorMessage, handleChange } = useField(nameRef, () => {}, {
  validateOnValueUpdate: false,
  validateOnMount: false,
})

const validationListeners = computed(() => {
  // If the field is valid or have not been validated yet
  // lazy
  if (!errorMessage.value) {
    return {
      blur: handleChange,
      change: handleChange,
      // disable `shouldValidate` to avoid validating on input
      input: (e) => handleChange(e, false),
    }
  }
  // Aggressive
  return {
    blur: handleChange,
    change: handleChange,
    input: handleChange, // only switched this
  }
})
</script>

<template>
  <div class="relative">
    <!-- <label :for="name" class="absolute -top-0.5 left-3 px-1 text-sm leading-3">{{ label }}</label> -->
    <div class="flex justify-between">
      <label :for="name" class="text-sm leading-3">{{ label }}</label>
      <p v-show="required" class="text-sm text-red-500">*</p>
    </div>
    <div class="">
      <input
        :id="name"
        class="form-control"
        :class="errorMessage && ' border-red-500 focus:ring-red-500 focus:border-red-500 focus:outline-none focus:ring-opacity-50'"
        :name="name"
        :type="type"
        v-on="validationListeners"
        v-model="value"
        @input="test"
      />
    </div>
    <div class="flex justify-between pt-0.5">
      <span class="text-red-500 text-sm">{{
        errorMessage
      }}</span>
      <span v-if="(typeof value) === 'string'" class="text-gray-500 text-sm">{{ value.trim().length }} / {{ max }}</span>
    </div>
  </div>
</template>

<style></style>
