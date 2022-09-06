<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/SignInUserSchema'
import { apiUser } from '../../../services/axios/api'

const { handleSubmit, values, meta, setFieldError, setErrors, errors } = useForm({
  validationSchema: schema,
  initialValues: {
    email: '',
    password: '',
  },
})

const onSubmit = handleSubmit(({ email, password}) => {
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
    localStorage.setItem('jwt', data.jwt);
    
    console.log(data);
    alert('Password Matched')
  } catch (error) {
    const { data, status } = error.response
    const { details, message } = data
    if(status === 401) setFieldError('password', message)
    if(status === 404) setFieldError('email', message)
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
      >Sign In</PrimaryButton
    >
  </div>
</template>

<style></style>