<script setup>
import BaseModal from '../../../../components/base/BaseModal.vue'
import { formatDatetime } from '../../../../utils/dateTime'
import schema from '@/services/validation/schema/SignInUserSchema'
import InputField from '../../../../components/base/form/InputField.vue';
import { useForm, ErrorMessage, Field } from 'vee-validate'
import { onUpdated } from 'vue';
const emits = defineEmits(['close','submit-form'])
const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
})
const { handleSubmit, values, resetForm } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit((user)=>{
  console.log('h');
  emits('submit-form', user)
})
onUpdated(()=> {
  if(props.show === true){
    setTimeout(resetForm,5)
  }
})
</script>

<template>
  <BaseModal :show="show" :width="'w-160'">
    <template #header>
      <h1 class="text-2xl font-semibold text-center">Add User</h1>
    </template>
    <template #body>
      <div class="flex flex-col">
        <label for="name">Name</label>
        <InputField id="name" class="" name="name" type="text" />
        <label for="email">Email</label>
        <InputField id="email" class="" name="email" type="text" />
        <ul class="flex gap-4">
          <span class="">Role</span>
          <li class="flex gap-1">
            <Field id="student" name="role" type="radio" value="student" />
            <label for="student">Student</label>
          </li>
          <li class="flex gap-1">
            <Field id="lecturer" name="role" type="radio" value="lecturer" />
            <label for="lecturer">Lecturer</label>
          </li>
          <li class="flex gap-1">
            <Field id="admin" name="role" type="radio" value="admin" />
            <label for="admin">Admin</label>
          </li>
        </ul>
      </div>
    </template>

    <template #footer>
      <button @click="onSubmit" class="bg-blue-500 p-2">Sign In</button>
      <button type="button" class="bg-red-500 p-2" @click="$emit('close')">
        Close
      </button>
    </template>
  </BaseModal>
</template>

<style></style>
