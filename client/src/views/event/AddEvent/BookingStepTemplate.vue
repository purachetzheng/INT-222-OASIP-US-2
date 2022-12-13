<script setup>
import { ref, computed } from 'vue'
const emits = defineEmits(['next', 'selectStep'])
const props = defineProps({
    formStep: {
        type: Number,
        default: 0,
    },
    thisStep: {
        type: Number,
        require: true,
    },
    completedSteps: {
        type: Number,
        default: 0,
    },
    // disabled: Boolean,
    stepComplete: Boolean,
    test: String
})

const expanded = computed(()=> props.thisStep === props.formStep)
const disabled = computed(()=> props.thisStep-1 > props.completedSteps)

</script>

<template>
    <div class="bg-white border rounded-md duration-300" :class="[disabled && 'brightness-90 pointer-events-none']">
        <div class="px-6 py-4 flex justify-between cursor-pointer" @click="$emit('selectStep', thisStep)">
            <div class="">
                <p class="font-semibold text-md">
                    <slot name="step-title">Choose Category</slot> {{thisStep}} {{formStep}} {{completedSteps}}
                </p>
                <p class="font- text-sm">
                    <slot name="step-desc">
                        Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    </slot>
                </p>
            </div>
            <div class="flex items-center">
                <fa-icon
                    :icon="['fas', 'chevron-right']"
                    class="fa-lg text-gray-400 duration-200"
                    :class="[expanded && 'rotate-90']"
                />
            </div>
        </div>

        <div v-show="expanded" class="px-6 pb-4">
            <slot />
            <div class="flex justify-end">
                <app-button btn-type="success" :disabled="!stepComplete" @click="$emit('next')">
                    <slot name="next-button-name">Next</slot>
                </app-button>
            </div>
        </div>
    </div>
    <!-- <div class="min-h-full flex flex-col"></div> -->
</template>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>
