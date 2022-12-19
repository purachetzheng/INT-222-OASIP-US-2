<script setup>

import { formatDatetime } from '../../../../utils/dateTime'
import schema from '../../../../services/validation/schema/EditCategorySchema'
import { useForm, ErrorMessage, Field } from 'vee-validate'
import { computed, onBeforeMount, onUpdated, ref } from 'vue'
import RoleSelectField from '../../../../components/user/RoleSelectField.vue'
import AddCategoryOwner from './AddCategoryOwner.vue'

const emits = defineEmits(['close', 'submit-edit-form'])
const props = defineProps({
    modalState: {
        type: Object,
        default: {
            // category:{}
        },
    },
    // editingCategory:{
    //   type: Object,
    //   default: {},
    // }
})

const formConfig = ref({
    isLoading: true,
    name: {
        maxLength: 100,
    },
    duration: {
        min: 1,
        max: 480,
    },
    description: {
        maxLength: 500,
    },
})

const formTemplate = {
    id: null,
    name: '',
    duration: 1,
    description: '',
}

// const formTemplate = {
//   name: props.modalState.value.category.eventCategoryName,
//   duration: props.modalState.value.category.eventDuration,
//   description: props.modalState.value.category.eventCategoryDescription
// }

const { handleSubmit, values, resetForm, meta, setFieldValue, setValues } =
    useForm({
        validationSchema: schema,
        initialValues: { ...formTemplate },
    })
// const onSubmit = handleSubmit(({name = '', email = '', role, password, confirmPassword}) => {
//   const isPasswordConfirm = password === confirmPassword
//   if (!isPasswordConfirm)
//     return setFieldError('confirmPassword', 'Passwords do not match, try again.')

//   const trimmedUser = {
//     name: name.trim(),
//     email: email.trim(),
//     role: role,
//     password: password,
//   }
//   emits('submit-form', trimmedUser)
// })

const onSubmit = handleSubmit(({ id, name, duration, description }) => {
    // console.log(values);
    const category = {
        id,
        eventCategoryName: name.trim(),
        eventDuration: duration,
        eventCategoryDescription: !description.trim()
            ? null
            : description.trim(),
    }
    emits('submit-edit-form', category)
})
onUpdated(async () => {
    if (props.modalState.visible) {
        const category = props.modalState.category
        setValues({
            id: category.id,
            name: category.eventCategoryName,
            duration: category.eventDuration,
            description: category.eventCategoryDescription || '',
        })
        return
    }
})
</script>

<template>
    <app-modal :show="modalState.visible">
        <template #header>
            <h1 class="text-2xl font-semibold text-center">Edit Category</h1>
            <!-- {{editingCategory}} -->
        </template>
        <template #body>
            <div class="flex flex-col gap-0 w-128">
                <app-vee-input
                    name="name"
                    type="text"
                    :max="formConfig.name.maxLength"
                    label="Name"
                    required
                    counter
                />
                <app-vee-input
                    name="duration"
                    type="number"
                    :max="formConfig.name.maxLength"
                    label="Duration"
                    required
                />
                <app-vee-textarea
                    name="description"
                    :max="formConfig.description.maxLength"
                    label="Description"
                    counter
                />
                <!-- <AddCategoryOwner /> -->
            </div>
        </template>
        <template #footer>
            <div class="flex gap-4">
                <app-button btn-type="success" @click="onSubmit"
                    >Submit</app-button
                >
                <app-button btn-type="secondary" @click="modalState.close" ghost
                    >Cancel</app-button
                >
            </div>
        </template>
    </app-modal>
</template>

<style scoped></style>
