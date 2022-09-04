<script setup>
import IcSunny from '@/assets/icons/weather-icons/IcSunny.vue'
import { useDarkmode } from '../../../stores'
import { storeToRefs } from 'pinia'
defineEmits([])
const props = defineProps({
  first: {
    type: String,
    require: true,
  },
})
const myDarkmode = useDarkmode()
const { darkmode } = storeToRefs(myDarkmode)
</script>

<template>
  <div
    class="flex p-1.5 bg-white dark:bg-gray-600 rounded-full cursor-pointer duration-300"
    @click="myDarkmode.toggleMode()"
  >
    <Transition name="switch">
      <IcSunny v-if="!darkmode" class="icon" />
      <fa-icon v-else :icon="['far', 'moon']" class="icon text-white" />
    </Transition>
  </div>
</template>

<style scoped>
.icon {
  @apply w-6 h-6;
}

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
