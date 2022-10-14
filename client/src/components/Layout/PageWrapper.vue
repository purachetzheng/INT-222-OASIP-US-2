<script setup>

</script>

<template>
    <div class="app-container">
      <div class="app-content">
        <slot />
      </div>
    </div>
</template>

<style scoped>
.app-wrapper{
    @apply min-h-screen w-full  flex flex-col bg-gradient-to-b from-slate-300 to-slate-100 dark:bg-gray-700
}
.app-container{
    @apply my-container h-full
}
.app-content{
    @apply py-8 h-full 
}
</style>
