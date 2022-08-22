<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/SignInUserSchema'
import { apiUser } from '../../../services/axios/api'

const { handleSubmit, values } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit(async (user) => {
  try {
    const { data } = await apiUser.post(user)
    console.log(data)
    alert('ok')
  } catch (error) {
    console.log(error)
    const { data, status } = error.response
    const { details } = data
    let messageError = ''
    details.forEach(details => {
        messageError+=`${details.field}: ${details.errorMessage}\n`
    });
    alert(messageError)
  }
})
</script>

<template>
  <main class="my-container h-full flex flex-col py-4 gap-4 justify-between">
    <div class="flex flex-col gap-2">
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
      {{ values }}
      <button @click="onSubmit" class="bg-blue-500 p-2">Sign In</button>
    </div>
  </main>
</template>

<style></style>
