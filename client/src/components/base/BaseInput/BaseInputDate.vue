<script setup>
defineEmits(['pickDate'])
const props = defineProps({
    name: {
        type: String,
        require: true
    },
    isDisable:{
        type:Boolean,
        default: false
    },
    value:{
        type:String,
        default: null
    }
    // selected: {
    //     type: String,
    //     require: true
    // },
    // options: {
    //     type: Array,
    //     require: true,
    // },
})
const id = `input-${props.name.toLowerCase()}`
</script>

<template>
    <input type="date" :name="name" :id="id" :disabled="isDisable" 
        :value="value"
        :class="[isDisable && 'input-disable']"
        class="block w-full px-2 h-9 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
        @change="$emit('pickDate', $event.target.value)"
    >

</template>
 
<style scoped>
/* input[type="date"]::-webkit-calendar-picker-indicator {
    background: transparent;
    bottom: 0;
    color: transparent;
    cursor: pointer;
    height: auto;
    left: 0;
    position: absolute;
    right: 0;
    top: 0;
    width: auto;
} */
.input-disable[type="date"]::-webkit-calendar-picker-indicator{
    background: transparent;
    bottom: 0;
    color: transparent;
    cursor: pointer;
    height: auto;
    left: 0;
    position: absolute;
    right: 0;
    top: 0;
    width: auto;
}
</style>