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
    },
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
const test = () => {
    closeSlideOver()
    // console.log('test');
}
</script>

<template>
    <Teleport to="#sidebar-div" v-if="isMounted">
        <Transition name="fade" @enter="openSlideOver" @leave="test">
            <div
                v-if="slideOverStage.show"
                class="absolute top-0 right-0 bottom-0 left-0 z-20 overflow-x-hidden"
            >
                <div
                    class="h-full w-full bg-black opacity-50 fixed"
                    @click="closeSlideOver"
                ></div>
                <div
                    class="slidein fixed right-0 w-128 h-full overflow-y-auto bg-white duration-500"
                    :class="[
                        slideOverContent.visible
                            ? 'translate-x-0 opacity-100'
                            : 'translate-x-128 opacity-0',
                    ]"
                >
                    <slot :close-slide-over="closeSlideOver" />
                </div>
            </div>
        </Transition>
    </Teleport>
</template>

<style scoped>
.slidein {
    transition: transform 0.3s ease-in-out, opacity 0.5s ease;
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
