<script setup>
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/SignInUserSchema'
import { apiUser } from '../../../services/api/lib'
import { useUserStore } from '../../../stores/'
import { useRouter } from 'vue-router'

const router = useRouter()
const userStore = useUserStore()
const { handleSubmit, values, meta, setFieldError, setErrors, errors } =
    useForm({
        validationSchema: schema,
        initialValues: {
            email: '',
            password: '',
        },
    })

const onSubmit = handleSubmit(({ email, password }) => {
    const user = {
        email: email.trim(),
        password: password,
    }
    signInUser(user)
})

const signInUser = async (user) => {
    try {
        await userStore.login(user)
        console.log('ok')
        alert('Password Matched')
        userStore.getUserInfo()
        router.push({ name: 'Home' })
    } catch (error) {
        const { data, status } = error.response
        const { details, message } = data
        if (status === 401) setFieldError('password', message)
        if (status === 404) setFieldError('email', message)
    }
}

// const getUser = async() => {
//   try{
//     const {data} = await apiUser.getById(1)
//     // myUser.
//     console.log(data);
//     // return data
//   }catch(error){
//     // const { data, status } = error.response
//     console.log(error);
//   }
// }
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
        
        <div class="flex items-center gap-4">
            <app-button
            btn-type="primary"
            @click="onSubmit"
            btn-size="lg"
            :disabled="!meta.valid"
            >
                Sign in
            </app-button>
            <p class="">or</p>
            <app-button
                btn-type="primary"
                @click=""
                btn-size="lg"
                :btn-icon="['fab', 'microsoft']"
                >
                Sign in with Microsoft
            </app-button>
        </div>
    </div>
</template>

<style></style>
