<script setup>
import { useField } from 'vee-validate'
import { toRef, computed } from 'vue'

defineEmits(['update:modelValue'])
const props = defineProps({
  name: {
    type: String,
    require: true,
  },
  type: {
    type: String,
    default: 'text',
  },
  label: {
    type: String,
    default: '',
  },
  required: {
    type: Boolean,
    default: false,
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
  <div class="">
    <label :for="name" class="">
      <label :for="name" class="text-sm leading-3">{{ label }}</label>
      <span v-show="required" class="text-sm text-red-500"> *</span></label
    >
    <input
      :id="name"
      :name="name"
      :type="type"
      v-on="validationListeners"
      v-model="value"
      class="form-control"
      :class="
        errorMessage &&
        ' border-red-500 focus:ring-red-500 focus:border-red-500 focus:outline-none focus:ring-opacity-50'
      "
    />
    <div class="flex justify-between pt-0.5">
      <span class="text-red-500 text-sm">{{ errorMessage || '&nbsp' }}</span>
      <span v-if="type === 'text' || type === 'email'" class="text-gray-500 text-sm">
        {{ value.trim().length }}/{{ max }}
      </span>
    </div>
  </div>
</template>

<style></style>
