<script setup>
import schema from '@/services/validation/schema/AddEventSchema'
import { useForm } from 'vee-validate'
import FillFormFileField from './FillFormFileField.vue'
const emits = defineEmits(['submit-form'])
const prop = defineProps({
    selectedCategory: {
        type: Object,
        require: true,
    },
})
const { handleSubmit } = useForm({
    validationSchema: schema,
    initialValues: {
        name: '',
        email: '',
        note: '',
    },
})
const onSubmit = handleSubmit((values) => {
    // console.log(values)
    emits('submit-form', values)
})
</script>

<template>
    <div class="mx-auto max-w-2xl">
        <div class="col-span-2">
            <div class="">
                <label for="category" class="text-sm font-medium leading-3">Category</label>
            </div>
            <input
                class="form-input h-10 mb-4"
                name="category"
                :value="selectedCategory.eventCategoryName"
                type="text"
                disabled
            />
        </div>
        <app-vee-input name="name" type="text" :max="100" label="Name" />
        <app-vee-input name="email" type="text" :max="100" label="Email" />
        <div class="grid grid-cols-2 gap-4"></div>
        <div class="grid grid-cols-2 gap-4">
            <app-vee-input name="datetime.date" type="date" label="Date" />
            <app-vee-input name="datetime.time" type="time" label="Time" />
        </div>
        <FillFormFileField />
        <app-vee-textarea name="notes" :max="500" label="Notes" />
    </div>
</template>

<style></style>
