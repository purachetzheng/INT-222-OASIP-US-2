<script setup>
defineEmits([])
const props = defineProps({
  step: {
    type: Number,
    default: 1,
  },
})

const progressList = [
  {
    step: 1,
    name: 'Select Category',
  },
  {
    step: 2,
    name: 'Fill Information',
  },
  {
    step: 3,
    name: 'Successfully',
  },
]
</script>

<template>
  <section class="flex flex-col items-center pb-8">
    <div class="flex-all-center w-full max-w-3xl">
      <template v-for="({ step: progressStep, name }, index) in progressList">
        <div
          class="circle flex-all-center"
          :class="progressStep <= step && 'active'"
        >
          <p class="number">Step {{ progressStep }}</p>
          <div class="title" :class="progressStep <= step && 'title-active'">
            {{ name }}
          </div>
        </div>
        <div
          v-if="index !== progressList.length - 1"
          class="line grow"
          :class="index + 1 < step ? 'visible' : 'invisible'"
        ></div>
      </template>
    </div>

    <!-- <div class="flex-all-center w-full max-w-3xl">
      <div class="circle flex-all-center active">
        <p class="progress-text">Step 1</p>
        <div class="title">Select Category</div>
      </div>
      <div class="line grow"></div>
      <div class="circle flex-all-center">
        <p class="progress-text">Step 2</p>
        <div class="title">Fill Information</div>
      </div>
      <div class="line grow invisible"></div>
      <div class="circle flex-all-center">
        <p class="progress-text">Step 3</p>
        <div class="title">Successfully</div>
      </div>
    </div> -->
  </section>
</template>

<style scoped>
.number {
  @apply font-medium;
}
.title {
  @apply text-sm font-medium absolute -bottom-8  whitespace-nowrap text-gray-400;
}
.title-active {
  @apply text-black;
}
.circle {
  @apply relative bg-gray-200 w-16 h-16 rounded-full border-4 border-gray-200 text-gray-600;
}
.line {
  @apply h-1 bg-blue-500;
}
.active {
  @apply border-blue-500 bg-white;
}
.flex-all-center {
  @apply flex justify-center items-center;
}
</style>
