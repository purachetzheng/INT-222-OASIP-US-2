<script setup>
import profilePlaceholder from '@/utils/profilePlaceholder'
import { roleProfile } from '@/config'
defineEmits([])
const props = defineProps({
  name: {
    type: String,
    require: true,
  },
  role: {
    type: String,
    require: true,
  }
})
</script>

<template>
  <div
    class="rounded-full flex items-center justify-center"
    :class="!role || roleProfile[role].profile"
  >
    <p class="font-sans font-semibold">
      {{ profilePlaceholder(name) }}
    </p>
  </div>
</template>

<style scoped>
.avatar-small{
    @apply w-10 h-10 
}
.avatar-medium{
    @apply w-20 h-20 text-3xl
}
</style>
