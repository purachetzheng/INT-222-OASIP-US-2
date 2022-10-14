<script setup>
import { useField } from 'vee-validate'
import { toRef, computed } from 'vue'
defineEmits([])
const props = defineProps({
  name: {
    type: String,
    require: true,
  },
  max: {
    type: Number,
    default: 999,
  },
  label: {
    type: String,
    default: '',
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
      <!-- <span v-show="required" class="text-sm text-red-500"> *</span> -->
    </label>
    <textarea
      :id="name"
      class="form-control"
      :class="errorMessage && 'bg-red-100 border-red-500'"
      :name="name"
      v-on="validationListeners"
      v-model="value"
      rows="6"
    ></textarea>
    <div class="flex justify-between pt-0.5">
      <span class="text-red-500 text-sm">{{ errorMessage || '&nbsp' }}</span>
      <span v-if="typeof value === 'string'" class="text-gray-500 text-sm"
        >{{ value.trim().length }} / {{ max }}</span
      >
    </div>
  </div>
</template>

<style></style>
