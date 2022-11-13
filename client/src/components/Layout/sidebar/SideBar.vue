<script setup>
import { collapsed, toggleSidebar, sidebarWidth } from './state'
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import navItemList from './navItemList'
import { useUserStore } from '../../../stores'
import { storeToRefs } from 'pinia'
import UserPanel from './UserPanel.vue'
const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const { user } = storeToRefs(userStore)
console.log(navItemList)
console.log(route.name)
console.log(user)
const allowedNavItems = computed(() =>
  navItemList.filter(
    (item) =>
      !item.mainItem.allowed ||
      item.mainItem.allowed.some((role) => role === user.value.role)
  )
)
</script>

<template>
  <aside class="h-screen bg-slate-200">
    <div class="sidebar px-3 py-3" :class="[collapsed ? 'w-[5rem]' : 'w-56']">
      <div id="sidebar-wrapper" class="flex flex-col h-full justify-between divide-y-2 divide-gray-500">
        <div class="sidebar-logo">
          <div class="w-max py-3 px-2.5" @click="toggleSidebar">
            <div class="w-32">
              <h1 class="font-bold">US-2</h1>
            </div>
          </div>
        </div>
        <!-- <hr> -->
        <div class="py-3">
          <user-panel :collapsed="collapsed" />
        </div>
        <!-- <hr class=" h-px border-0 bg-gray-700"> -->
        <nav id="sidebar-body" class="grow ">
          <ul class="mt-2 space-y-2 tracking-wide">
            <li
              class="min-w-max"
              v-for="{ mainItem, subItems } in allowedNavItems"
            >
              <router-link
                :to="{ name: mainItem.route }"
                class="sidebar-nav-item block"
                :class="[route.name === mainItem.route && 'active']"
              >
                <div class="h-6 w-6 flex justify-center items-center">
                  <fa-icon :icon="mainItem.icon" class="h-5 w-5" />
                </div>
                <div
                  class="font-medium relative w-0 whitespace-nowrap"
                  :class="[collapsed ? 'text-collapsed' : 'max-w-full']"
                >
                  {{ mainItem.name }}
                  <!-- <div class="flex w-36 justify-between pr-3.5">
                    <p>
                      <fa-icon :icon="['fas', 'chevron-down']" class="fa-xs" />
                    </p>
                  </div> -->
                </div>
              </router-link>
            </li>
          </ul>
        </nav>
        <!-- <div class="sidebar-footer">
          <a
            href="#"
            class="group flex items-center space-x-4 rounded-md px-4 py-3 text-gray-600"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5 group-hover:fill-cyan-600"
              viewBox="0 0 20 20"
              fill="currentColor"
            >
              <path
                fill-rule="evenodd"
                d="M11.49 3.17c-.38-1.56-2.6-1.56-2.98 0a1.532 1.532 0 01-2.286.948c-1.372-.836-2.942.734-2.106 2.106.54.886.061 2.042-.947 2.287-1.561.379-1.561 2.6 0 2.978a1.532 1.532 0 01.947 2.287c-.836 1.372.734 2.942 2.106 2.106a1.532 1.532 0 012.287.947c.379 1.561 2.6 1.561 2.978 0a1.533 1.533 0 012.287-.947c1.372.836 2.942-.734 2.106-2.106a1.533 1.533 0 01.947-2.287c1.561-.379 1.561-2.6 0-2.978a1.532 1.532 0 01-.947-2.287c.836-1.372-.734-2.942-2.106-2.106a1.532 1.532 0 01-2.287-.947zM10 13a3 3 0 100-6 3 3 0 000 6z"
                clip-rule="evenodd"
              />
            </svg>
            <span class="group-hover:text-gray-700">Settings</span>
          </a>
        </div> -->
      </div>
    </div>
  </aside>
</template>

<style>
:root {
  --sidebar-bg-color: #2f855a;
  --sidebar-item-hover: #38a169;
  --sidebar-item-active: #276749;
}
</style>

<style scoped>
.sidebar-transition {
  @apply transition-all duration-300 ease-in-out;
}
.sidebar {
  @apply sidebar-transition h-full ;
}
#sidebar-footer {
  @apply w-max -mb-3;
}
.sidebar-nav-item {
  @apply flex items-center space-x-4 pl-4 py-3 text-gray-600 hover:bg-gray-200 rounded-md;
}
.sidebar-nav-item.active {
  @apply bg-gradient-to-r from-sky-600 to-cyan-400 text-white;
}
.text-collapsed {
  @apply opacity-0 -translate-x-8;
}
.sidebar-nav-item div {
  transition: opacity 150ms ease-in-out, transform 200ms linear;
}
</style>
