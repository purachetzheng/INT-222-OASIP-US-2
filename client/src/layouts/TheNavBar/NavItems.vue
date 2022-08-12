<script setup>
defineEmits([])
const props = defineProps({
    item: {
        type: String,
        require: true,
    },
    routeTo: {
        type: String,
        require: true,
    }
})

</script>
 
<template>
    <div class="">
        {{ item }}
    </div>
</template>
 
<style>
</style>