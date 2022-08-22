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
    default: ''
  },
  attribute: {
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
    <label :for="name">{{ label }}</label>
    <input
      :id="name"
      class="form-control"
      :class="errorMessage && 'bg-red-100 border-red-500'"
      :name="name"
      :type="type"
      :max="attribute.max"
      v-on="validationListeners"
      v-model="value"
    />
    <span
      v-show="errorMessage"
      class="absolute right-1 -bottom-5 text-red-500 text-sm"
      >{{ errorMessage }}</span>
  </div>
</template>

<style></style>
