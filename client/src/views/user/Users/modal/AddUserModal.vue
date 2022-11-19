<script setup>
import schema from '@/services/validation/schema/AddUserSchema'
import { useForm, ErrorMessage, Field } from 'vee-validate'
import { onUpdated } from 'vue'
import RoleSelectField from '../../../../components/user/RoleSelectField.vue'

const emits = defineEmits(['close', 'submit-form'])
const props = defineProps({
    show: {
        type: Boolean,
        default: false,
    },
})
const { handleSubmit, values, resetForm, meta, setFieldValue } = useForm({
    validationSchema: schema,
    initialValues: {
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
    },
})
const onSubmit = handleSubmit(
    ({ name = '', email = '', role, password, confirmPassword }) => {
        const isPasswordConfirm = password === confirmPassword
        if (!isPasswordConfirm)
            return setFieldError(
                'confirmPassword',
                'Passwords do not match, try again.'
            )

        const trimmedUser = {
            name: name.trim(),
            email: email.trim(),
            role: role,
            password: password,
        }
        emits('submit-form', trimmedUser)
    }
)
onUpdated(() => {
    if (props.show === true) {
        setTimeout(resetForm, 5)
    }
})
</script>

<template>
    <app-modal :show="show">
        <template #header>
            <h1 class="text-2xl font-semibold text-center">Add User</h1>
        </template>
        <template #body>
            <div class="flex flex-col gap-0">
                <app-vee-input
                    name="name"
                    type="text"
                    :max="100"
                    label="Name"
                    required
                    counter
                />
                <app-vee-input
                    name="email"
                    type="email"
                    :max="50"
                    label="Email"
                    required
                    counter
                />
                <RoleSelectField />
                <app-vee-input
                    name="password"
                    type="password"
                    :max="14"
                    label="Password"
                    required
                    counter
                />
                <app-vee-input
                    name="confirmPassword"
                    type="password"
                    :max="14"
                    label="Confirm Password"
                    required
                    counter
                />
            </div>
        </template>

        <template #footer>
            <div class="flex gap-4 justify-start items-center">
                <app-button
                    btn-type="primary"
                    btn-size="md"
                    :disabled="!meta.valid"
                    @click="onSubmit"
                    >Create</app-button
                >
                <app-button
                    btn-type="secondary"
                    btn-size="md"
                    @click="$emit('close')"
                    >Close</app-button
                >
            </div>
        </template>
    </app-modal>
</template>

<style scoped></style>
