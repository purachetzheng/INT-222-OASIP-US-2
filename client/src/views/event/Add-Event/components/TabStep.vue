<script setup>
defineEmits(['set-step'])
const props = defineProps({
  stepNow: {
    type: Number,
    default: 1,
  },
  stepInfo: {
    type: Object,
    require: true,
  },
  stepSetter: {
    type: Function,
    require: true,
  },
})
</script>

<template>
  <ul class="flex flex-col gap-2">
    <li
      class="w-full"
      v-for="step in stepInfo"
      :key="step.id"
    >
      <button
        class="w-full p-2 border-2 flex items-center gap-2 rounded-lg"
        :class="[stepNow === step.id ? 'border-blue-500' : 'border-gray-500']"
        @click="$emit('set-step', step.id)"
        :disabled="stepNow < step.id"
      >
        <div
          class="w-10 h-10 bg-gray-200 rounded-xl flex items-center justify-center"
        >
          <fa-icon
            :icon="step.icon"
            class="fa-lg"
            :class="[stepNow === step.id ? 'text-blue-500' : 'text-gray-500']"
          />
        </div>
        <div class="pr-2 text-left">
          <p class="text-sm">Step {{ step.id }}/3</p>
          <p class="font-medium whitespace-nowrap">{{ step.name }}</p>
        </div>
      </button>
    </li>
  </ul>
</template>

<style></style>
