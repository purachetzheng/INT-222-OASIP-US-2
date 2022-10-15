<script setup>
import { useDarkmode } from '../../../stores'
import { storeToRefs } from 'pinia'
const myDarkmode = useDarkmode()
const { darkmode } = storeToRefs(myDarkmode)
</script>
 
<template>
<header class="py-2 bg-gray-100 dark:bg-gray-700">
    <div class="flex justify-between items-center px-6">
        <div class="">
            Breadcrumbs > Event
        </div>
        <div class="flex gap-4">
            <div class="flex p-2 rounded-full hover:bg-gray-200">
                <fa-icon :icon="['fas', 'circle']" class="fa-lg text-black" />
            </div>
            
            <button class="flex p-2 rounded-full hover:bg-gray-200 relative" @click="myDarkmode.toggleMode()">
                <Transition name="switch">
                    <fa-icon :icon="['far', 'sun']" class="fa-lg" v-if="!darkmode" />
                    <fa-icon :icon="['far', 'moon']" class="fa-lg" v-else />
                </Transition>
                
            </button>
            
        </div>
    </div>
</header>
</template>
 
<style scoped>
/* switch transitions */
.switch-enter-from,
.switch-leave-to {
  opacity: 0;
  transform: translateY(5px);
}
.switch-enter-active {
  transition: all 0.3s ease;
}
.switch-leave-active {
  transition: all 0.3s ease;
  position: absolute;
  height: 20%;
}
</style>