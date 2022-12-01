<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useUserStore, useAppStore } from '../../../stores'
import { storeToRefs } from 'pinia'
import UserMenu from './UserMenu.vue'
const router = useRouter()
const route = useRoute()


const appStore = useAppStore()
// const { appDarkMode } = appStore
// const { darkMode } = storeToRefs(appStore)

const userStore = useUserStore()
const { user, isAuth } = storeToRefs(userStore)
</script>
 
<template>
<header class="py-2 bg-slate-200 dark:bg-gray-700">
    <div class="flex justify-between items-center px-6">
        <div class="">
            <p class="font-semibold text-lg">{{route.name}}</p>
        </div>
        <div class="flex gap-4 items-center">
            <div class="flex p-2 rounded-full hover:bg-gray-200 h-10 w-10">
                <!-- <fa-icon :icon="['fas', 'circle']" class="fa-lg text-black" /> -->
                <img src="../../../assets/images/flags/united-kingdom.png" alt="h-full w-full">
            </div>
            
            <button class="flex p-2 rounded-full hover:bg-gray-200 relative h-10 w-10" @click="appStore.darkMode.toggle()">
                <Transition name="switch">
                    <fa-icon :icon="['far', 'sun']" class="h-full w-full" v-if="!appStore.darkMode.stage" />
                    <fa-icon :icon="['far', 'moon']" class="h-full w-full" v-else />
                </Transition>
            </button>
            
            <UserMenu />
            <!-- <fa-icon :icon="['far', 'user']" class="fa-lg text-black" /> -->
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