<script setup>
import { onMounted, reactive, ref } from 'vue'

defineEmits([])
const props = defineProps({
    slideOverStage: {
        type: Object,
        default: {
            show: null,
            // close: () => {},
        },
    },
})

const slideOverContent = reactive({
    visible: false,
    show: () => {
        slideOverContent.visible = true
    },
    hide: () => {
        slideOverContent.visible = false
    }
})
// for fix slide animation
const closeSlideOver = async () => {
    slideOverContent.hide()
    setTimeout(props.slideOverStage.close)
}
const openSlideOver = () => {
    setTimeout(slideOverContent.show)
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
            <Transition name="fade" @enter="openSlideOver">
                <div
                    v-if="slideOverStage.show"
                    class="absolute top-0 right-0 bottom-0 left-0 z-20 overflow-x-hidden"

                >
                <div class="h-full w-full bg-black opacity-50 fixed" @click="closeSlideOver"></div>
                    <div
                        class="slidein fixed right-0 w-128 h-full overflow-y-auto bg-white duration-500"
                        :class="[
                            slideOverContent.visible ? 'translate-x-0' : 'translate-x-96',
                        ]"
                    >
                        <!-- <Transition name="fade" duration="1000"> -->
                        <component
                            :is="Component"
                            :class="slideOverContent.visible ? 'opacity-100' : 'opacity-0'"
                            class="transition-opacity duration-700 ease "
                            :close-slide-over="closeSlideOver"
                        />
                        <!-- </Transition> -->
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
