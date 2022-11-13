<script setup>
import { storeToRefs } from 'pinia'
import { computed } from 'vue';
import { useUserStore } from '../../../stores'
import PlaceholderAvatar from '../../user/PlaceholderAvatar.vue'
const props = defineProps({
  collapsed: {
    type: Boolean,
    require: true,
  },
})
const userStore = useUserStore()
const { user, isAuth } = storeToRefs(userStore)

const userInfo = computed(()=> 
  isAuth.value ? user.value : { name: 'Guest', role: 'guest'}
)
</script>

<template>
  <div class="flex gap-3 items-center pl-1">
    <PlaceholderAvatar
      class="avatar-md"
      :name="userInfo.name"
      :role="userInfo.role"
    />
    <div class="collapsed-transition absolute left-20" :class="[collapsed ? 'text-collapsed' : 'max-w-full']">
      <h5 class="text-sm font-medium whitespace-nowrap text-gray-700">{{ userInfo.name }}</h5>
      <p v-show="isAuth" class="text-xs font-medium whitespace-nowrap text-gray-600">{{ userInfo.role }}</p>
    </div>
  </div>
</template>

<style scoped>
.text-collapsed {
  @apply opacity-0 -translate-x-8;
}
.collapsed-transition {
  transition: opacity 150ms ease-in-out, transform 200ms linear;
}
</style>
