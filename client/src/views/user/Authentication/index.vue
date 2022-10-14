<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import schema from '@/services/validation/schema/AddUserSchema'
import { apiUser } from '../../../services/api/lib'
import { ref } from 'vue'
import SignUp from './SignUp.vue'
import SignIn from './SignIn.vue'
import AuthBanner from './AuthBanner.vue'
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
      setFieldError('confirmPassword', 'Passwords do not match, try again.')
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
const [isSignUp, goSignIn, goSignUp] = [
  ref(false),
  () => (isSignUp.value = false),
  () => (isSignUp.value = true),
]
</script>

<template>
  <main class="my-container h-full flex flex-col py-4 gap-4 justify-between">
    <TransitionGroup
      tag="div"
      class="max-w-7xl h-160 mx-auto bg-white rounded-lg flex overflow-hidden"
      :class="isSignUp ? 'sign-up' : 'sign-in'"
    >
      
      <section
        :key="'login'"
        class="animation bg-white overflow-auto w-160 p-10"
      >
        <SignUp v-if="isSignUp" />
        <SignIn v-else />
      </section>
      <section
        :key="'banner'"
        class="animation bg-blue-500 w-min"
      >
        <AuthBanner :isSignUp="isSignUp" @go-sign-in="goSignIn()" @go-sign-up="goSignUp()" />
      </section>
    </TransitionGroup>
  </main>
</template>

<style scoped>
.sign-up{
    @apply flex-row
}
.sign-in{
    @apply flex-row-reverse
}
.animation{
    @apply duration-500
}
</style>
