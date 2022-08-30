<script setup>
defineEmits([])
const props = defineProps({
        title:{
        type:String,
        default: 'Button'
   },
})

</script>
 
<template>
    <button class="btn">
        <slot></slot>
    </button>
</template>
 
<style scoped>
.btn {
    @apply px-6 py-2 rounded-lg font-semibold select-none
}
.btn-border{
    @apply border-2 
}
.btn-indigo{
    @apply text-indigo-500 border-indigo-500 
}
.btn-indigo:hover{
    @apply text-white bg-indigo-600 border-indigo-600 
}
.btn-danger{
    @apply text-red-500 border-red-500 
}
.btn-danger:hover{
    @apply text-white bg-red-600 border-red-600 
}
.btn-gray{
    @apply bg-gray-100 border-gray-100
}
.btn-gray:hover{
    @apply bg-gray-200 border-gray-200
}
</style>