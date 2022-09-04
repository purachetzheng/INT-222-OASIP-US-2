<script setup>
defineEmits(['selectCategory'])
const props = defineProps({
    name: {
        type: String,
        require: true
    },
    selected: {
        type: String,
        require: true
    },
    options: {
        type: Array,
        require: true,
    },
})
const id = `dropdown-${props.name.toLowerCase()}`
</script>

<template>
    <select :id="id" class="block w-full px-2 h-9 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
        @change="$emit('selectCategory', $event.target.value)">
        <option class="" selected>{{ selected }}</option>
        <option class="" v-for="option in options" :value="option">{{ option }}</option>
    </select>

</template>
 
<style scoped>

</style>