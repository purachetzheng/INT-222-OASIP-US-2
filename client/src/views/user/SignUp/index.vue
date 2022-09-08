<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/AddUserSchema'
import { apiUser } from '../../../services/api/lib'
import RoleSelectField from '../Users/components/RoleSelectField.vue'

const { handleSubmit, values, meta, setFieldError, setErrors } = useForm({
  validationSchema: schema,
  initialValues: {
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
  },
})
// {name: 'Test', email: 'test@test.com', password: 'dsadsadasdasd', confirmPassword: 'sadasdasdasdas', role: 'admin'}
const onSubmit = handleSubmit(({name, email, password, confirmPassword, role}) => {
  const isPasswordConfirm = password === confirmPassword
  if(!isPasswordConfirm) {
    setFieldError('confirmPassword', 'Passwords do not match, try again.')
  }
  const user = {
    name: name.trim(),
    email: email.trim(),
    password: password,
    role: role
  }
  createUser(user)
})
const createUser = async(user) => {
try {
    const { data } = await apiUser.post(user)
    console.log(data)
    alert('ok')
  } catch (error) {
    console.log(error)
    const { data, status } = error.response
    const { details } = data
    let messageError = ''
    details.forEach((details) => {
      messageError += `${details.field}: ${details.errorMessage}\n`
    })
    alert(messageError)
  }
}
</script>

<template>
  <main class="my-container h-full flex flex-col py-4 gap-4 justify-between">
    <!-- <div class="flex flex-col gap-2">
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
    </div> -->

    <div class="max-w-5xl mx-auto bg-white rounded-lg flex">
      <!-- <div class="bg-blue-400 w-80 h-full">d</div> -->
      <div class="p-10">
        <h2 class="text-2xl font-bold mb-6">Sign In</h2>
        <div class="flex flex-col gap-2">
          <RoleSelectField label="I am a/an" />
          <InputField
            class=""
            name="name"
            type="text"
            label="Name"
            :max="100"
            :required="true"
          />
          <InputField
            class=""
            name="email"
            type="text"
            label="Email"
            :max="50"
            :required="true"
          />
          <InputField
            class=""
            name="password"
            type="password"
            label="Password"
            :max="14"
            :required="true"
          />
          <InputField
            class=""
            name="confirmPassword"
            type="password"
            label="Confirm Password"
            :max="14"
            :required="true"
          />
        </div>
        <PrimaryButton type="submit" @click="onSubmit" class="btn btn-indigo duration-300 submit-btn" 
          :disabled="!meta.valid" >Create</PrimaryButton>
      </div>
    </div>
  </main>
</template>

<style></style>
