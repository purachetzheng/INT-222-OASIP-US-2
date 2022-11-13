<script setup>
import { useField } from 'vee-validate'
import InputField from '../../../../components/base/form/InputField.vue'
import InputFieldNoValidate from '../../../../components/base/form/InputFieldNoValidate.vue'
import { datetimeForInput } from '../../../../utils/dateTime';
defineEmits([])
const props = defineProps({
  //   first: {
  //     type: String,
  //     require: true,
  //   },
})
const inputDate = 'datetime.date'
const inputTime = 'datetime.time'
const { value, errorMessage, handleChange } = useField('datetime', null, {
  validateOnValueUpdate: false,
//   validateOnMount: false,
})


</script>

<template>
  <div class="">
    <label for="text-sm">Datetime</label>
    <div class="flex justify-between gap-4">
      <InputFieldNoValidate
        name="datetime.date"
        type="date"
        class="w-full"
        :class="
          errorMessage &&
          ' border-red-500 focus:ring-red-500 focus:border-red-500 focus:outline-none focus:ring-opacity-50'
        "
        :min="datetimeForInput.getNow()"
        :max="datetimeForInput.getMoreMonths(3)"
      />
      <InputFieldNoValidate
        class="w-full"
        :class="
          errorMessage &&
          ' border-red-500 focus:ring-red-500 focus:border-red-500 focus:outline-none focus:ring-opacity-50'
        "
        name="datetime.time"
        type="time"
      />
    </div>
    <div class="flex justify-between pt-0.5">
      <span class="text-red-500 text-sm">{{ errorMessage }}&nbsp;</span>
    </div>
  </div>
</template>

<style></style>
