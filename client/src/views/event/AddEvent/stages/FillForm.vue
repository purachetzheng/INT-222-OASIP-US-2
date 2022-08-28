<script setup>
import { computed, inject, ref } from 'vue'
import { useForm } from 'vee-validate'
import InputField from '../../../../components/base/form/InputField.vue'
import TextAreaField from '../../../../components/base/form/TextAreaField.vue'
import schema from '@/services/validation/schema/AddEventSchema'
import InputDatetime from '../components/InputDatetime.vue'
const emits = defineEmits(['submit-form'])
const props = defineProps({
  // categories: {
  //   type: Array,
  //   require: true,
  // },
})
const selectedCategoryId = inject('selectedCategoryId')
const selectedCategory = inject('selectedCategory')

const { handleSubmit } = useForm({
  validationSchema: schema,
  initialValues: {
    name: '',
    email: '',
    note: ''
  },
})
const onSubmit = handleSubmit((values) => {
  emits('submit-form', values)
})
</script>

<template>
  <section class="flex flex-col justify-between items-center">
    <div class="flex flex-col max-w-3xl gap-0">
      <div class="flex flex-col w-160">
        <label for="">Category</label>
        <input
          class="form-input"
          name="category"
          :value="selectedCategory.eventCategoryName"
          type="text"
          disabled
        />
      </div>

      <div class="flex flex-col w-160">
        <InputField class="" name="name" type="text" :max="100" label="Name" />
      </div>
      <div class="flex flex-col">
        <InputField class="" name="email" type="text" :max="100" label="Email" />
      </div>
      <div class="flex flex-col">
        <InputDatetime />
      </div>
      <div class="flex flex-col">
        <TextAreaField class="" name="notes" :max="500" label="Notes" />
      </div>
    </div>
    <div class="">
      <button @click="onSubmit" class="bg-blue-500 p-2">Next</button>
    </div>
  </section>
</template>

<style></style>
