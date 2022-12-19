<script setup>

</script>

<template>
    <div class="container mx-auto px-4 h-full bg-gray-100">
        <div class="flex flex-col lg:flex-row items-center justify-center h-full ">
            <div class="lg:mr-20 h-60 lg:h-100">
                <img alt="" class=" h-full w-full" src="../assets/images/illustrations/error-illustration2.svg" >
            </div>
            <div class="flex flex-col items-center lg:items-start mt-10 lg:mt-0">
                <div class="text-6xl font-medium">404</div>
                <div class="text-lg lg:text-2xl font-medium mt-6"> Oops. This page has gone missing. </div>
                <div class="text-md mt-4 font-medium"> You may have mistyped the address or the page may have moved. </div>
                
                <router-link :to="{name: 'Home'}">
                    <app-button class="mt-6" btn-type="outline-primary" btn-size="lg">Back to Home</app-button>
                </router-link>
            </div>
        </div>
    </div>
</template>

<style></style>
