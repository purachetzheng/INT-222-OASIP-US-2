<script setup>
import { onMounted, ref } from 'vue'

defineEmits([])
const props = defineProps({
    sidebarStage: {
        type: Object,
        default: {
            visible: null,
        },
    },
})

const showSidebar = ref(false)

// for fix slide animation
const closeSidebar = () => {
    showSidebar.value = false
    setTimeout(() => (props.sidebarStage.visible = false), 50)
}
const openSidebar = () => {
    setTimeout(() => (showSidebar.value = true), 50)
}
// for fix teleport
const isMounted = ref(false)
onMounted(() => {
    isMounted.value = true
})
</script>

<template>
    <Teleport to="#sidebar-div" v-if="isMounted">
        <router-view v-slot="{ Component }">
            <Transition name="fade" @enter="openSidebar">
                <div
                    v-if="sidebarStage.visible"
                    class="absolute top-0 right-0 bottom-0 left-0 z-40 overflow-x-hidden"
                    style="background-color: rgba(0, 0, 0, 0.5)"
                    @click="closeSidebar"
                >
                    <div
                        class="slidein absolute z-50 top-0 right-0 w-96 h-full overflow-y-auto bg-white duration-500"
                        :class="[
                            showSidebar ? 'translate-x-0' : 'translate-x-96',
                        ]"
                    >
                        <component :is="Component" />
                        <!-- <p v-for="num in 50">Lorem</p> -->
                    </div>
                </div>
            </Transition>
        </router-view>
    </Teleport>
</template>

<style scoped>
.slidein {
    transition: all 0.3s ease-in-out;
}

/* .slide-enter-active,
.slide-leave-active {
    transition: transform 0.2s ease;
}

.slide-enter,
.slide-leave-to {
    transform: translateX(100%);
    transition: all 300ms ease-in 0s;
} */
.slide-enter,
.slide-leave-active {
    right: -100%;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

.bounce-enter-active {
    animation: bounce-in 0.5s;
}
.bounce-leave-active {
    animation: bounce-in 0.5s reverse;
}
@keyframes bounce-in {
    0% {
        transform: scale(0);
    }
    50% {
        transform: scale(1.25);
    }
    100% {
        transform: scale(1);
    }
}
</style>
