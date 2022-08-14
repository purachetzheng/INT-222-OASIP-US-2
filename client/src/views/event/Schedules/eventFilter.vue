<script setup>
import { BaseDropdown, BaseInputDate } from '../../../components/base';
import { IcSortRound } from '../../../assets/icons/editing-icons';
defineEmits([])
const props = defineProps({
    first: {
        type: String,
        require: true,
    },
})
const test = (e) => console.log(e);
</script>
 
<template>
    <div class="flex gap-2 justify-center">
        <IcSortRound class="w-8 h-8 mr-4" />
        <div class="w-60">
            <BaseDropdown name="category" selected="All Category" :options="['test1', 'test2']"
                @select-category="test" />
        </div>
        <div class="w-40">
            <BaseDropdown name="event-status" selected="All Events" :options="['test1', 'test2']"
                @select-category="test" />
        </div>
        <div class="w-56">
            <BaseInputDate name="event-date" @pick-date="test" />
        </div>
    </div>
</template>
 
<style>
</style>