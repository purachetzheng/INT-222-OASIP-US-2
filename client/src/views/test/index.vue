<script setup>
import { ref } from 'vue';




const test = ref(true)
</script>
 
<template>
<TransitionGroup name="list" tag="ul" class="flex w-min justify-between" :class="test? 'flex-row': 'flex-row-reverse'">
    <div class="duration-500 transition-all bg-red-500 w-60 h-100">1</div>
    <div class="duration-500 transition-all bg-blue-500 w-60 h-100">2</div>
</TransitionGroup>
<button @click="test= !test">swap</button>

</template>
 
<style scoped>
.list-enter-active,
.list-leave-active {
  transition: all 5s ease;
}
.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>