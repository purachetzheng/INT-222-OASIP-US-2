<script setup>
defineEmits(['go-sign-in', 'go-sign-up'])
const props = defineProps({
    isSignUp: {
      type: Boolean,
      require: true,
    },
})
</script>

<template>
  <!-- <Transition name="fade"> -->
  <div
    v-if="isSignUp"
    class="w-80 h-full text-white px-10 flex flex-col justify-center items-center gap-6"
  >
    <h5 class="text-lg text-center font-semibold">Already Signed up?</h5>
    <p class="text-sm text-center">
      Lorem ipsum dolor sit, amet consectetur adipisicing elit. Rem debitis
      incidunt fuga.
    </p>
    <SecondaryButton class="btn btn-border" @click="$emit('go-sign-in')">
      Sign In
    </SecondaryButton>
  </div>

  <div
    v-else
    class="w-80 h-full text-white px-10 flex flex-col justify-center items-center gap-6"
  >
    <h5 class="text-lg text-center font-semibold">
      Don't Have an Account Yet?
    </h5>
    <p class="text-sm text-center">
      Lorem ipsum dolor sit amet consectetur adipisicing elit. Reprehenderit,
      facere!
    </p>
    <SecondaryButton class="btn btn-border" @click="$emit('go-sign-up')">
      Sign Up
    </SecondaryButton>
  </div>
  <!-- </Transition> -->
</template>

<style scoped>
    .fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
