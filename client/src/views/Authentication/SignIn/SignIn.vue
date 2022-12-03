<script setup>
import AuthPageTemplate from '../AuthPageTemplate.vue'
import schema from '@/services/validation/schema/SignInUserSchema'
import { useForm } from 'vee-validate'
import { useUserStore } from '../../../stores';


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
        await userStore.signIn(user)
        alert('Password Matched')
    } catch (error) {
        console.log(error.message);
        const { data, status } = error.response
        const { details, message } = data
        if (status === 401) setFieldError('password', message)
        if (status === 404) setFieldError('email', message)
    }
}
</script>

<template>
    <AuthPageTemplate>
        <template #img>
            <img
                src="../../../assets/images/illustrations/login-illustration.svg"
                alt=""
            />
        </template>
        <app-vee-input name="email" type="email" :placeholder="'Email'" />
        <app-vee-input name="password" type="password" :placeholder="'Password'" />

        <app-button class="mt-4" btn-size="lg" @click="onSubmit"> Sign In </app-button>

        <div class="text-center mt-4">
            <div class="text-sm font-medium">
                <span class="">Don't have Account? </span>
                <router-link
                    :to="{ name: 'Home' }"
                    class="text-indigo-500 underline"
                >
                    Create account
                </router-link>
            </div>
        </div>
        <div class="flex my-6 items-center gap-4">
            <div class="h-px flex-1 bg-slate-200 dark:bg-navy-500"></div>
            <p>OR</p>
            <div class="h-px flex-1 bg-slate-200 dark:bg-navy-500"></div>
        </div>
        <app-button
            btn-type="outline-primary"
            @click=""
            btn-size="lg"
            :btn-icon="['fab', 'microsoft']"
        >
            Sign in with Microsoft
        </app-button>
    </AuthPageTemplate>
</template>

<style></style>
