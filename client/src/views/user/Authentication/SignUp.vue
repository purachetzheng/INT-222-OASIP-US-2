<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/AddUserSchema'
import { apiUser } from '../../../services/api/lib'
import RoleSelectField from '../../../components/user/RoleSelectField.vue'

// defineEmits([])
// const props = defineProps({
//   first: {
//     type: String,
//     require: true,
//   },
// })

const { handleSubmit, values, meta, setFieldError, setErrors } = useForm({
  validationSchema: schema,
  initialValues: {
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
  },
})
// {name: 'Test', email: 'test@test.com', password: 'dsadsadasdasd', confirmPassword: 'sadasdasdasdas', role: 'admin'}
const onSubmit = handleSubmit(
  ({ name, email, password, confirmPassword, role }) => {
    const isPasswordConfirm = password === confirmPassword
    if (!isPasswordConfirm) {
      return setFieldError('confirmPassword', 'Passwords do not match, try again.')
    }
    const user = {
      name: name.trim(),
      email: email.trim(),
      password: password,
      role: role,
    }
    createUser(user)
  }
)

const createUser = async (user) => {
  try {
    const { data } = await apiUser.post(user)
    console.log(data)
    alert('Your user has been registered')
  } catch (error) {
    console.log(error)
    const { data, status } = error.response
    const { details } = data
    details.forEach((details) => {
      setFieldError(details.field, details.errorMessage)
    })
  }
}
</script>

<template>
  <div class="">
    <h2 class="text-2xl font-bold mb-6">Sign Up</h2>
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
    <PrimaryButton
      type="submit"
      @click="onSubmit"
      class="btn btn-indigo duration-300 submit-btn"
      :disabled="!meta.valid"
      >Create</PrimaryButton
    >
  </div>
</template>

<style></style>