<script setup>
import { useField } from 'vee-validate';
import { toRef, computed } from 'vue';

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
    require: true,
  }
})

const nameRef = toRef(props, 'name');
const { value, errorMessage, handleChange } = useField(nameRef, ()=>{} ,{
  validateOnValueUpdate: false, validateOnMount: false,
})

const validationListeners = computed(() => {
  // If the field is valid or have not been validated yet
  // lazy
  if (!errorMessage.value) {
    return {
      blur: handleChange,
      change: handleChange,
      // disable `shouldValidate` to avoid validating on input
      input: e => handleChange(e, false),
    };
  }
  // Aggressive
  return {
    blur: handleChange,
    change: handleChange,
    input: handleChange, // only switched this
  };
});
</script>

<template>
  <div class="relative mb-6">
    <label :for="name">{{label}}</label>
    <input class="form-control" :id="name" :name="name" :type="type" v-on="validationListeners" v-model="value" />
    <span v-show="errorMessage" class="absolute right-1 -bottom-6 text-red-500 text-sm">{{errorMessage}}</span>
  </div>
</template>

<style></style>
