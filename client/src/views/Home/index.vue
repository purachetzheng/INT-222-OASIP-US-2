<script setup>
import { useDarkmode } from '../../stores'
import { storeToRefs } from 'pinia'
const myDarkmode = useDarkmode()
const { darkmode } = storeToRefs(myDarkmode)
// myDarkmode.init()
</script>

<template>
  <main class="my-container">
    <div>
      <h1 class="text-3xl font-bold underline ">
        {{ $t('hello') }}
      </h1>
      <button @click="myDarkmode.toggleMode()">toggle</button>
    </div>
    <h1>{{ darkmode }}</h1>
    <fa-icon icon="fa-solid fa-user-secret" />
    <fa-icon icon="coffee" />
    <fa-icon icon="times" />
      <fa-icon :icon="['fas', 'circle']" class="text-red-500" />
    <fa-layer class="fa-4x">
      <fa-icon :icon="['fas', 'circle']" class="text-red-500" />
      <fa-icon :icon="['fas', 'times']" transform="shrink-8" inverse />
    </fa-layer>
    <fa-layer class="fa-xl">
      <fa-icon :icon="['far', 'circle']" class="" />
      <fa-icon :icon="['fas', 'plus']" transform="shrink-6" />
    </fa-layer>
    <fa-layer class="fa-4x">
      <fa-icon :icon="['fas', 'tag']" class="text-green-500" />
      <fa-layer-text
        :icon="['fas', 'times']"
        value="NEW"
        transform="shrink-12 rotate--45" class="text-white"
        inverse
      />
    </fa-layer>
  </main>
</template>

<style></style>
