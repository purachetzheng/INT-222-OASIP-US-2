<script setup>
defineEmits([])
const props = defineProps({
  text: {
    type: String,
    require: true,
  },
  attr: {
    type: Object,
    default: {
      rows: '10',
      maxlength: '500',
      placeholder: 'Note',
      id: 'event-notes',
    },
  },
})
</script>

<template>
  <textarea
    class="block w-full px-2 py-1 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500"
    name=""
    :id="attr.id"
    :placeholder="attr.placeholder"
    :maxlength="attr.maxlength"
    :rows="attr.rows"
    ></textarea
  >
</template>

<style></style>
