<script setup>
import AuthPageTemplate from './AuthPageTemplate.vue'
import schema from '@/services/validation/schema/RegisterSchema'
import { useForm } from 'vee-validate'
import { useUserStore } from '../../stores';
import { ref } from 'vue';
import RoleSelectField from './RoleSelectField.vue';

const isLoading = ref(false)
const userStore = useUserStore()
const { handleSubmit, values, meta, setFieldError, setErrors } = useForm({
  validationSchema: schema,
  initialValues: {
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
  },
})
const onSubmit = handleSubmit(
  ({ name, email, password, confirmPassword, role }) => {
    console.log({name, email, password, role});
    const user = {
      name: name.trim(),
      email: email.trim(),
      password: password,
      role: role,
    }
    signUpUser(user)
  }
)

const signUpUser = async (user) => {
  try {
    // const { data } = await apiUser.post(user)
    await userStore.signUp(user)
    
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
    <AuthPageTemplate>
        <template #header>Welcome To OASIP</template>
        <template #desc>Please sign up to continue</template>
        <template #img>
            <img
                src="../../assets/images/illustrations/Curious-bro.svg"
                alt=""
            />
        </template>
        <RoleSelectField />
        <app-vee-input name="name" type="name" placeholder="Name" label="Name" />
        <app-vee-input name="email" type="email" placeholder="Email" label="Email" />
        <app-vee-input name="password" type="password" placeholder="Password" label="Password" />
        <app-vee-input name="confirmPassword" type="password" placeholder="Confirm Password" label="Confirm Password" />

        <app-button class="mt-4" btn-size="lg" @click="onSubmit"> Sign In </app-button>

        <div class="text-center mt-4">
            <div class="text-sm font-medium">
                <span class="text-gray-500">Already have an account? </span>
                <router-link
                    :to="{ name: 'SignIn' }"
                    class="text-indigo-500 underline"
                >
                    Sign In
                </router-link>
            </div>
        </div>

    </AuthPageTemplate>
</template>

<style></style>
