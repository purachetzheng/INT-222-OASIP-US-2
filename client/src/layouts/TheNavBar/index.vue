<script setup>
import './style.css'
import NavItems from './NavItems.vue';
</script>
 
<template>
    <header class="bg-white w-full">
        <div class="myContainer flex items-center justify-between py-2">
            <div class="font-bold text-2xl">OASIP</div>

            <ul class="flex gap-4">
                <!-- <NavItems item="Home" route-to="/" /> -->
                <router-link :to="{ name: 'Home' }">Home</router-link>
                <router-link :to="{ name: 'Schedules' }">Schedules</router-link>
            </ul>

            <div class=""></div>
        </div>
    </header>
</template>
 
<style>
</style>