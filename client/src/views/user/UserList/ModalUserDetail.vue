<script setup>
import { useForm } from 'vee-validate'
import schema from '@/services/validation/schema/EditUserSchema'
import PlaceholderAvatar from '../../../components/user/PlaceholderAvatar.vue'
import RoleBadge from '../../../components/user/RoleBadge.vue'
import RoleSelectField from '../../../components/user/RoleSelectField.vue'
import { datetimeCalculate, datetimeCheck, formatDatetime } from '../../../utils/dateTime'
import { onUpdated } from 'vue'
defineEmits([])
const props = defineProps({
    viewDetailModal: {
        type: Object,
        require: true,
    },
})
const { handleSubmit, values, resetForm, meta, setValues, setFieldError } = useForm({
    validationSchema: schema,
    validateOnMount: false,
})

const onSubmitForm = handleSubmit(({name = '', email = '', role }) => {
    const editUser = {}
    name = name.trim()
    email = email.trim()
    const isNameChanged = props.viewDetailModal.user.name !== name
    const isEmailChanged = props.viewDetailModal.user.email !== email
    const isRoleChanged = props.viewDetailModal.user.role !== role

    if (isNameChanged) editUser.name = name
    if (isEmailChanged) editUser.email = email
    if (isRoleChanged) editUser.role = role

    const isUserChanged = Object.keys(editUser).length !== 0
    if (!isUserChanged) return props.viewDetailModal.onSuccess()

    props.viewDetailModal.onEdit({
        id: props.viewDetailModal.user.id,
        modifyUser: editUser,
        setErr: setFieldError,
    })
})

onUpdated(() => {
    if (props.viewDetailModal.state) {
        resetForm()
        setValues({
            name: props.viewDetailModal.user.name,
            email: props.viewDetailModal.user.email,
            role: props.viewDetailModal.user.role,
        })
    }
})
</script>

<template>
    <app-modal
        :show="viewDetailModal.state"
        type="blank"
    >
        <template #header>
            <div class=""></div>
        </template>

        <template v-if="!viewDetailModal.editingMode" #footer>
            <div class="flex gap-4 justify-center items-center">
                <app-button btn-type="primary" btn-size="md" @click="viewDetailModal.editingOn">
                    Edit
                </app-button>
                <app-button btn-type="secondary" ghost btn-size="md" @click="viewDetailModal.close">
                    Close
                </app-button>
            </div>
        </template>
        <template v-if="viewDetailModal.editingMode" #footer>
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
        </template>
        <template v-if="viewDetailModal.editingMode" #body>
            <div class="flex flex-col">
                <app-vee-input name="name" type="text" label="Name" :max="100" counter required />
                <app-vee-input name="email" type="text" label="Email" :max="100" counter required />
                <RoleSelectField />
            </div>
        </template>
        <template v-if="!viewDetailModal.editingMode" #body>
            <div class="flex flex-col gap-4">
                <div class="flex justify-center gap-8">
                    <PlaceholderAvatar
                        class="avatar-medium"
                        :name="viewDetailModal.user.name"
                        :role="viewDetailModal.user.role"
                    />
                    <div class="flex flex-col items-start justify-center gap-2">
                        <h1 class="text-2xl font-semibold text-center">
                            {{ viewDetailModal.user.name }}
                        </h1>
                        <RoleBadge :title="viewDetailModal.user.role" />
                    </div>
                </div>
                <div class="flex flex-col">
                    <div class="flex text-sm">
                        <span class="font-semibold text-blue-600 w-32">Email: </span>
                        <p class="font-medium col-span-2">
                            {{ viewDetailModal.user.email }}
                        </p>
                    </div>
                    <div class="flex text-sm">
                        <span class="font-semibold text-blue-600 w-32">Created Date: </span>
                        <p class="font-medium col-span-2">
                            {{
                                formatDatetime.monthDayYearHourTime(viewDetailModal.user.createdOn)
                            }}
                        </p>
                    </div>
                    <div class="flex text-sm">
                        <span class="font-semibold text-blue-600 w-32">Updated Date: </span>
                        <p class="font-medium col-span-2">
                            {{
                                formatDatetime.monthDayYearHourTime(viewDetailModal.user.updatedOn)
                            }}
                        </p>
                    </div>
                </div>
            </div>
        </template>
    </app-modal>
</template>

<style></style>
