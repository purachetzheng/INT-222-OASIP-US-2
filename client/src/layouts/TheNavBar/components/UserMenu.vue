<script setup>
import { storeToRefs } from 'pinia'
import PlaceholderAvatar from '../../../components/user/PlaceholderAvatar.vue'
import { useUserStore } from '../../../stores'
const userStore = useUserStore()
const { user, isAuth } = storeToRefs(userStore)
</script>

<template>
  <div class="relative z-20 group">
    <div class="flex gap-2 items-center">
      <div class="text-right">
        <h5 class="text-sm font-medium">{{ user.name }}</h5>
        <p class="text-xs font-medium">{{ user.role }}</p>
      </div>
      <PlaceholderAvatar
        class="avatar-small"
        :name="user.name"
        :role="user.role"
      />
    </div>
    <ul
      id="user-menu"
      class="absolute hidden group-hover:flex bg-white py-2 top-11 rounded-md shadow-md"
    >
      <li class="">
        <button class="hover:bg-gray-100 px-4 py-2" @click="userStore.logout">
            <p class="whitespace-nowrap">Sign Out</p>
        </button>
      </li>
    </ul>
  </div>
</template>

<style>
#user-menu::before {
  content: '';
  position: absolute;
  top: -6px;
  left: 50%;
  width: 0;
  height: 0;
  -webkit-transform: translateX(-50%);
  -ms-transform: translateX(-50%);
  transform: translateX(-50%);
  border-right: 6px solid transparent;
  border-bottom: 6px solid #fff;
  border-left: 6px solid transparent;
}
</style>
