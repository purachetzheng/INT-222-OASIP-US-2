<script setup>
import { computed, inject, ref } from 'vue'
import { useForm, ErrorMessage } from 'vee-validate'
import InputField from '../../../../components/base/form/InputField.vue'
import TextAreaField from '../../../../components/base/form/TextAreaField.vue'
import schema from '@/services/validation/schema/AddEventSchema'
const emits = defineEmits(['submit-form'])
const props = defineProps({
  // categories: {
  //   type: Array,
  //   require: true,
  // },
})
const selectedCategoryId = inject('selectedCategoryId')
const selectedCategory = inject('selectedCategory')

const { handleSubmit } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit((values) => {
  emits('submit-form', values)
})
</script>

<template>
  <section class="flex flex-col justify-between items-center">
    <div class="flex flex-col max-w-3xl gap-2">
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
        <label for="">Name</label>
        <InputField class="" name="name" type="text" />
      </div>
      <div class="flex flex-col">
        <label for="">Email</label>
        <InputField class="" name="email" type="text" />
      </div>
      <div class="flex flex-col">
        <label for="">Datetime</label>
        <div class="flex justify-between gap-4">
          <InputField class="w-full" name="datetime.date" type="date" />
          <InputField class="w-full" name="datetime.time" type="time" />
        </div>
        <ErrorMessage
          name="datetime"
          class="text-right text-red-500"
          as="div"
        />
      </div>
      <div class="flex flex-col">
        <label for="">Notes</label>
        <TextAreaField class="" name="notes" />
      </div>
    </div>
    <div class="">
      <button @click="onSubmit" class="bg-blue-500 p-2">Next</button>
    </div>
  </section>
</template>

<style></style>
