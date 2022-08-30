<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/AddUserSchema'
import { apiUser } from '../../../services/axios/api'
import RoleSelectField from '../Users/components/RoleSelectField.vue'
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
  ref(true),
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
        :key="'banner'"
        class="animation bg-blue-500 z-10 w-min"
      >
        <AuthBanner :isSignUp="isSignUp" @go-sign-in="goSignIn()" @go-sign-up="goSignUp()" />
        <!-- <Transition name="fade"> -->
          <!-- <div
            v-if="isSignUp"
            class="w-80 h-full text-white px-10 flex flex-col justify-center items-center gap-6"
          >
            <h5 class="text-lg text-center font-semibold">
              Already Signed up?
            </h5>
            <p class="text-sm text-center">
              Lorem ipsum dolor sit, amet consectetur adipisicing elit. Rem
              debitis incidunt fuga.
            </p>
            <SecondaryButton class="btn btn-border" @click="goSignIn">
              Sign In
            </SecondaryButton>
          </div>

          <div
          v-else
            class="w-80 h-full text-white px-10 flex flex-col justify-center items-center gap-6"
          >
            <h5 class="text-lg text-center font-semibold">
                Don't Have an Account Yet?
            </h5>
            <p class="text-sm text-center">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Reprehenderit, facere!
            </p>
            <SecondaryButton class="btn btn-border" @click="goSignUp">
              Sign Up
            </SecondaryButton>
          </div> -->
        <!-- </Transition> -->
      </section>
      <section
        :key="'login'"
        class="animation bg-white overflow-auto w-160 p-10"
      >
        <SignUp v-if="isSignUp" />
        <SignIn v-else />
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
