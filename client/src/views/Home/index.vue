<script setup>
import { useDarkmode } from '../../stores'
import { storeToRefs } from 'pinia'
import PageWrapper from '../../components/Layout/PageWrapper.vue';
const myDarkmode = useDarkmode()
const { darkmode } = storeToRefs(myDarkmode)
// myDarkmode.init()
</script>

<template>
  <PageWrapper>
  <div class="">
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
    <fa-layer class="fa-2xl">
      <fa-icon :icon="['fas', 'filter']" class="text-red-500" />
      <fa-icon :icon="['fas', 'filter']" class="fa-2xs"  inverse />
    </fa-layer>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
    <h1>1</h1>
  </div>
</PageWrapper>
</template>

<style></style>
