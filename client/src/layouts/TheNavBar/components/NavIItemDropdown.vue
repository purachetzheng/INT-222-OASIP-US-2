<script setup>
import { ref } from 'vue'
import IcArrowDown from '@/assets/icons/arrows-icons/IcArrowDown.vue'
import { useUserStore } from '../../../stores';
import { storeToRefs } from 'pinia';
defineEmits([])
const props = defineProps({
  mainItem: {
    type: Object,
    require: true,
  },
  subItems: {
    type: Array,
    default: [],
  },
})
const userStore = useUserStore()
const { user } = storeToRefs(userStore)
const isHover = ref(false)
</script>

<template>
  <li
    v-if="!mainItem.allowed || mainItem.allowed.some(role => role === user.role)"
    class="relative group hover:bg-gray-100 rounded-full"
    @mouseenter="isHover = true"
    @mouseleave="isHover = false"
  >
    <router-link
      :to="{ name: mainItem.route }"
      class="flex items-center gap-1 px-4 py-2 font-semibold group-hover:text-blue-500"
      >{{ mainItem.name }}

      <IcArrowDown v-if="subItems.length !== 0" class="h-6 w-6 group-hover:-rotate-180 duration-300" />
    </router-link>

    <Transition duration="550" name="nested">
      <div v-if="subItems.length !== 0"
        v-show="isHover"
        class="absolute top-11 p-1.5 bg-white border-2 duration-300 shadow-md rounded-2xl z-10"
      >
        <ul class="flex flex-col w-max max-w-xs">
          <li v-for="item in subItems">
          <router-link :to="{ name: item.route }" class="flex gap-4 p-4 items-center rounded-2xl hover:bg-gray-200">
            <!-- <component :is="item.icon" class="basis-10 -mt-2 w-10 h-10"></component> -->
            <fa-icon :icon="item.icon" class=" fa-xl" />
            
                <p class="font-semibold">{{ item.name }}</p>
            <!-- <div class="flex flex-col item-center gap-1">
                <p class="font-semibold text-sm">{{ item.desc }}</p>
            </div> -->
          </router-link>
          </li>
        </ul>
      </div>
    </Transition>
  </li>
</template>

<style>
.nested-enter-active,
.nested-leave-active {
  transition: all 0.2s ease-in-out;
}
/* delay leave of parent element */
.nested-leave-active {
  transition-delay: 0.15s;
}

.nested-enter-from,
.nested-leave-to {
  transform: translateY(30px);
  opacity: 0;
}

/* we can also transition nested elements using nested selectors */
.nested-enter-active .inner,
.nested-leave-active .inner {
  transition: all 0.2s ease-in-out;
}
/* delay enter of nested element */
.nested-enter-active .inner {
  transition-delay: 0.15s;
}

.nested-enter-from .inner,
.nested-leave-to .inner {
  transform: translateX(20px);
  /*
  	Hack around a Chrome 96 bug in handling nested opacity transitions.
    This is not needed in other browsers or Chrome 99+ where the bug
    has been fixed.
  */
  opacity: 0.001;
}
</style>
