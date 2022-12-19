<script setup>
import { useForm } from 'vee-validate'
import schema from '@/services/validation/schema/AddUserSchema'
import PlaceholderAvatar from '../../../components/user/PlaceholderAvatar.vue'
import RoleBadge from '../../../components/user/RoleBadge.vue'
import RoleSelectField from '../../../components/user/RoleSelectField.vue'
import { datetimeCalculate, datetimeCheck, formatDatetime } from '../../../utils/dateTime'
import { onUpdated } from 'vue'
defineEmits([])
const props = defineProps({
    addUserModal: {
        type: Object,
        require: true,
    },
})
const { handleSubmit, values, resetForm, meta, setFieldValue } = useForm({
    validationSchema: schema,
    validateOnMount: false,
    initialValues: {
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
    },
})
const onSubmit = handleSubmit(({ name = '', email = '', role, password, confirmPassword }) => {
    const isPasswordConfirm = password === confirmPassword
    if (!isPasswordConfirm)
        return setFieldError('confirmPassword', 'Passwords do not match, try again.')

    const trimmedUser = {
        name: name.trim(),
        email: email.trim(),
        role: role,
        password: password,
    }
    console.log(trimmedUser)
    props.addUserModal.onCreate(trimmedUser)
})

onUpdated(() => {
    if (props.addUserModal.state) {
        setTimeout(resetForm, 5)
    }
})
</script>

<template>
    <app-modal :show="addUserModal.state" type="blank">
        <template #header>
            <h1 class="text-2xl font-semibold text-center">Add User</h1>
        </template>

        <template #footer>
            <div class="flex gap-4 justify-center items-center">
                <app-button
                    btn-type="primary"
                    btn-size="md"
                    @click="onSubmit"
                    :disabled="!meta.valid"
                >
                    Create
                </app-button>
                <app-button btn-type="secondary" ghost btn-size="md" @click="addUserModal.close">
                    Close
                </app-button>
            </div>
        </template>
        <!-- <template v-if="viewDetailModal.editingMode" #footer>
            <div class="flex gap-4 justify-center items-center">
                <app-button btn-type="success" btn-size="md" @click="onSubmitForm">
                    Save
                </app-button>
                <app-button
                    btn-type="secondary"
                    ghost
                    btn-size="md"
                    @click="viewDetailModal.onCancel"
                >
                    Cancel
                </app-button>
            </div>
        </template> -->
        <template #body>
            <div class="flex flex-col gap-0">
                <app-vee-input name="name" type="text" :max="100" label="Name" required counter />
                <app-vee-input name="email" type="email" :max="50" label="Email" required counter />
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
    </app-modal>
</template>

<style></style>
