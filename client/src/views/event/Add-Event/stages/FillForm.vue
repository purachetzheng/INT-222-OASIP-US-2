<script setup>
import { computed, inject, ref } from 'vue'
import { useForm } from 'vee-validate'
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
