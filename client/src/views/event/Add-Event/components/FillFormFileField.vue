<script setup>
import { ErrorMessage, Field, useField } from 'vee-validate'
import { computed, ref } from 'vue'

defineEmits([])
const props = defineProps({
    // first: {
    //     type: String,
    //     require: true,
    // },
})
const nameRef = ref('file')
const { value, errorMessage, handleChange, handleBlur, setValue } = useField(
    nameRef,
    // () => {},
    // {
    //     validateOnValueUpdate: false,
    //     validateOnMount: false,
    // }
)
// const oldFile = null
// const validationListeners = computed(() => {
//     // If the field is valid or have not been validated yet
//     // lazy
//     console.log(value.value)
//     if (value.value && value.value.size > 10 * 1024 * 1024) {
//         console.log('size')
//         let test = null
//         alert('size')
//         return {
//             blur: handleChange,
//             change: (e) => handleChange(test, false),
//             input: (e) => handleChange(test, false),
//         }
//     }
//     if (!errorMessage.value) {
//         console.log('ok');
//         return {
//             blur: handleChange,
//             change: handleChange,
//             // disable `shouldValidate` to avoid validating on input
//             input: (e) => handleChange(e, false),
//         }
//     }
//     console.log('ok');
//     // Aggressive
//     return {
//         blur: handleChange,
//         change: handleChange,
//         input: handleChange, // only switched this
//     }
// })

const fileSizeUnit = ['byte', 'kb', 'mb', 'gb']
const fileSizeConvert = ({number, fromUnit, toUnit}) => {
    if(fromUnit === toUnit) return number
    const thresh = 1024
    const fromUnitLevel = fileSizeUnit.indexOf(fromUnit)
    const fromToUnit = fileSizeUnit.indexOf(toUnit)
    const diff = fromUnitLevel - fromToUnit
    return number * Math.pow(diff >= 0? thresh : 1/thresh, Math.abs(diff))
}
const isFileSizeGreaterThan = (fileSize, number, unit) => {
    return fileSize > fileSizeConvert({number: number, fromUnit: unit, toUnit: 'byte'})
}

const file = ref(undefined)
const inputCoolDown = ref(false)
const addFile = (newFile) => {
    console.log(newFile);
    if(!newFile) return
    if(isFileSizeGreaterThan(newFile.size, 10, 'mb')) 
        return alert('The file size cannot be larger than 10 MB.')
    file.value = newFile
    setValue(newFile)
}
const removeFile = () => {
    file.value = undefined
    inputCoolDown.value = true
    setTimeout(()=> inputCoolDown.value = false, 200)
    setValue(undefined)
}

const onInput = (e) => addFile(e.target.files[0])

</script>

<template>
    <!-- <div class="flex flex-col">
        <Field name="file">
            <input type="file" v-on="validationListeners" />
        </Field>
        <input type="file" name="" id="" @input="onInput">
        <span class="text-red-500 text-sm">{{ errorMessage || '&nbsp' }}</span>
    </div> -->
    <div class="">
        {{value}}
        <label for="dropzone-file" class="flex flex-col justify-center items-center w-full h-32 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
            <div class="flex flex-col justify-center items-center pt-5 pb-6" v-if="!file">
                <svg aria-hidden="true" class="mb-3 w-10 h-10 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                <p class="mb-2 text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Click to upload</span> or drag and drop</p>
                <p class="text-xs text-gray-500 dark:text-gray-400">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
            </div>
            <div class="flex items-center gap-2" v-else>
                <div class="">
                    <p class="">File name: {{file.name}}</p>
                    <p class="">File size: 
                        {{ fileSizeConvert({number: file.size, fromUnit: 'byte', toUnit: 'mb'}).toFixed(2) }}
                        MB
                    </p>
                </div>
                <button class=" w-8 h-8 rounded-full hover:bg-red-100" @click="removeFile">
                    <fa-icon :icon="['fas', 'xmark']" class="fa-lg text-red-500" />
                </button>
            </div>
            <input id="dropzone-file" class="hidden" type="file" :disabled="inputCoolDown" @change.prevent="onInput" />
        </label>
    </div>
</template>

<style></style>
