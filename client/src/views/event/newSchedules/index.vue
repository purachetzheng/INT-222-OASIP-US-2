<script setup>
import BaseDropdown from '../../../components/base/BaseDropdown/index.vue';</script>

<template>
  <main
    class="my-container h-full flex flex-col py-4 gap-4 justify-between test"
  >
    <h1 class="text-center text-3xl font-bold">Booking</h1>
    <div class="flex gap-2 justify-between">
        <!-- <button :id="id" class="block w-full px-2 h-9 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500">
            All
        </button> -->
        <button class="block h-10 transition duration-100 ease-in-out border rounded shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none focus:ring-opacity-50 disabled:opacity-50 disabled:cursor-not-allowed text-black placeholder-gray-400 bg-white border-gray-300 focus:border-blue-500">
            <span class="flex gap-4 items-center px-3">
                <span class="">All</span>
                <fa-icon :icon="['fas', 'fa-chevron-down']" class="fa-2xs" />
            </span>
        </button>
    </div>
  </main>
</template>

<style></style>
