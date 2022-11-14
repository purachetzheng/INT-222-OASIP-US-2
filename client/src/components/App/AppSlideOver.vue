<script setup>
import { onMounted, reactive, ref, watch } from 'vue'

defineEmits(['close'])
const props = defineProps({
    // slideOverStage: {
    //     type: Object,
    //     default: {
    //         show: null,
    //         // close: () => {},
    //     },
    // },
    show: {
        type: Boolean,
        default: false
    },
})

// for fix teleport
const isMounted = ref(false)
onMounted(() => {
    isMounted.value = true
})

</script>

<template>
    <Teleport to="#sidebar-div" v-if="isMounted">
        <div
            class="absolute top-0 left-0 z-20"
            :class="[show && 'right-0 bottom-0']"
        >
            <Transition name="fade">
                <div
                    v-if="show"
                    class="h-full w-full bg-black opacity-50 fixed"
                    @click="$emit('close')"
                ></div>
            </Transition>
            <div
                class="slide fixed right-0 w-128 h-full overflow-y-auto bg-white duration-500"
                :class="[show ? 'slide-in' : 'slide-out']"
                style=""
            >
                <Transition name="fade">
                    <slot v-if="show" />
                </Transition>
            </div>
        </div>
    </Teleport>
</template>

<style scoped>
.slide {
    transition: all 0.3s ease-in-out, opacity 0.5s ease;
}
.slide-in {
    transform: none;
    /* @apply translate-x-0 opacity-100 */
}
.slide-out {
    transform: translate3d(100%, 0, 0);
    /* @apply translate-x-128 opacity-0 */
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

</style>
