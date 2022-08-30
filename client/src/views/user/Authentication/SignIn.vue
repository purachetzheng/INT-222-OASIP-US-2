<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/SignInUserSchema'
import { apiUser } from '../../../services/axios/api'
import RoleSelectField from '../Users/components/RoleSelectField.vue'

// defineEmits([])
// const props = defineProps({
//   first: {
//     type: String,
//     require: true,
//   },
// })

const { handleSubmit, values, meta, setFieldError, setErrors, errors } = useForm({
  validationSchema: schema,
  initialValues: {
    email: '',
    password: '',
  },
})
// {name: 'Test', email: 'test@test.com', password: 'dsadsadasdasd', confirmPassword: 'sadasdasdasdas', role: 'admin'}
const onSubmit = handleSubmit(({ email, password}) => {
    console.log({ email, password});
    // const isPasswordConfirm = password === confirmPassword
    // if (!isPasswordConfirm) {
    //   setFieldError('confirmPassword', 'Passwords do not match, try again.')
    // }
    const user = {
      email: email.trim(),
      password: password,
    }
    signInUser(user)
  }
)

const signInUser = async (user) => {
  try {
    const { data } = await apiUser.signIn(user)
    alert('ok')
  } catch (error) {
    console.log(error)
    const res = error.response
    const { data, status } = error.response
    const { details, message } = data
    // let messageError = ''
    // details.forEach((details) => {
    //   messageError += `${details.field}: ${details.errorMessage}\n`
    // })
    alert(message)
  }
}
</script>

<template>
  <div class="">
    <h2 class="text-2xl font-bold mb-6">Sign In</h2>
    <div class="flex flex-col gap-2">
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