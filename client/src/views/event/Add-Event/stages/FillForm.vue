<script setup>
import { computed, inject, ref } from 'vue'
import { ErrorMessage, Field, useForm } from 'vee-validate'
import InputField from '../../../../components/base/form/InputField.vue'
import TextAreaField from '../../../../components/base/form/TextAreaField.vue'
import schema from '@/services/validation/schema/AddEventSchema'
import InputDatetime from '../components/InputDatetime.vue'
const emits = defineEmits(['submit-form'])
const props = defineProps({
    // categories: {
    //   type: Array,
    //   require: true,
    // },
    addEventForm: {
        type: Object,
        require: true,
    },
})
const selectedCategoryId = inject('selectedCategoryId')
// const selectedCategory = inject('selectedCategory')
const selectedCategory = 'test'

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
    <section class="flex flex-col justify-between items-center">
        <div class="flex flex-col max-w-3xl gap-0">
            <div class="flex flex-col w-160 gap-2">
                <label for="" class="text-sm leading-3">Category</label>
                <input
                    class="form-input mb-4"
                    name="category"
                    :value="addEventForm.selectedCategory.eventCategoryName"
                    type="text"
                    disabled
                />
            </div>
            <app-vee-input name="name" type="text" :max="100" label="Name" />
            <app-vee-input name="email" type="text" :max="100" label="Email" />
            <div class="grid grid-cols-2 gap-4">
                <app-vee-input name="datetime.date" type="date" label="Date" />
                <app-vee-input name="datetime.time" type="time" label="Time" />
            </div>
            <Field name="file" v-slot="{ handleChange, handleBlur }">
                <input type="file" @change="handleChange" @blur="handleBlur" />
            </Field>
            <ErrorMessage class="text-red-500" name="file" />

            <!-- <div class="flex justify-center items-center w-full">
                <label for="dropzone-file" class="flex flex-col justify-center items-center w-full h-64 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                    <div class="flex flex-col justify-center items-center pt-5 pb-6">
                        <svg aria-hidden="true" class="mb-3 w-10 h-10 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                        <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Click to upload</span> or drag and drop</p>
                        <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
                    </div>
                    <input id="dropzone-file" type="file" class="hidden" />
                </label>
            </div>  -->
            <app-vee-textarea name="notes" :max="500" label="Notes" />
        </div>
        <div
            class="flex w-full justify-between flex-row-reverse gap-4 mt-4"
            v-show="addEventForm.step == 2"
        >
            <app-button button-type="success" @click="onSubmit">
                Book
            </app-button>
            <app-button
                v-if="addEventForm.step > 1"
                button-type="no-border"
                :button-icon="['fas', 'arrow-right']"
                @click="addEventForm.prevStep"
            >
                Back
            </app-button>
        </div>
    </section>
</template>

<style></style>
