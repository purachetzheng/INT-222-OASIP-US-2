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
  // label: {
  //   type: String,
  //   default: '',
  // },
  // max: {
  //   type: Number,
  //   default: 999,
  // },
  attr: {
    type: Object,
    default: {},
  },
})

const nameRef = toRef(props, 'name')
const { value } = useField(nameRef, () => {}, {})
</script>

<template>
  <input
    :id="name"
    class="form-control"
    :name="name"
    :type="type"
    :maxlength="max"
    v-model="value"
    @input="test"
  />
</template>

<style></style>
