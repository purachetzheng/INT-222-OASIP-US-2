<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/SignInUserSchema'
import { apiUser } from '../../../services/api/lib'
import { useUserStore } from '../../../stores/'
import { useRouter } from 'vue-router'

import { useIsAuthenticated } from '../../../services/MSAL/composition-api/useIsAuthenticated'
import {useMsalAuthentication } from '../../../services/MSAL/composition-api/useMsalAuthentication'
// import { useMsal } from '../../../composition-api/useMsal';

import SignInMS from './SignInMS.vue'
import { InteractionRequiredAuthError, InteractionStatus } from '@azure/msal-browser'
import { loginRequest } from '../../../authConfig'
import { useMsal } from '../../../services/MSAL/composition-api/useMsal'

const isAuthenticated = useIsAuthenticated()
const { accounts,  instance, inProgress,   } = useMsal();

const name = computed(() => {
    if (accounts.value.length > 0) {
        const name = accounts.value[0].name;
        console.log(accounts.value[0]);
        if (name) {
            return name.split(" ")[0];
        }
    }
    return "";
});


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
        alert('Password Matched')
        userStore.loadUser()
        router.push({ name: 'Home' })
    } catch (error) {
        console.log(error.message);
        const { data, status } = error.response
        const { details, message } = data
        if (status === 401) setFieldError('password', message)
        if (status === 404) setFieldError('email', message)
    }
}
const getA = () => console.log(accounts.value[0]);
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
            <SignInMS />
        </div>
        <!-- <app-button @click="">Test</app-button>
        <div class="flex flex-col">
            <p>isAuthenticated: {{isAuthenticated}}</p>
            <p>inProgress: {{inProgress}}</p>
            <p>name: {{name}}</p>
        </div>
        <app-button class="" @click="getA">Get accounts</app-button> -->
    </div>
</template>

<style></style>
