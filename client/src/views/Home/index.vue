<script setup>
import { useDarkmode } from '../../stores'
import { storeToRefs } from 'pinia'
const myDarkmode = useDarkmode()
const { darkmode } = storeToRefs(myDarkmode)
myDarkmode.init()



</script>
 
<template>
  <div>
    <div class="">
      <h1 class="text-3xl font-bold underline">
        {{ $t("hello") }}
      </h1>

      <button @click="myDarkmode.toggleMode()">toggle</button>

    </div>
  </div>
  <h1>{{ darkmode }}</h1>
</template>
 
<style>
</style>