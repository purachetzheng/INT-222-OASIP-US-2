<script setup>
import { storeToRefs } from 'pinia';
import { useUserStore } from '../../../stores'
import PlaceholderAvatar from '../../user/PlaceholderAvatar.vue';

const userStore = useUserStore()
const { user, isAuth } = storeToRefs(userStore)
</script>
 
<template>
<div class="flex gap-3 items-center">
    <PlaceholderAvatar
        class="avatar-small"
        :name="user.name"
        :role="user.role"
      />
      <div class="">
        <h5 class="text-sm font-medium">{{ user.name }}</h5>
        <p class="text-xs font-medium">{{ user.role }}</p>
      </div>
      
    </div>
</template>
 
<style>

</style>