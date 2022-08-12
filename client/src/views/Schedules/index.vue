<script setup>
// import { getEvent } from '../../services/axios/api/event'
// import apiEvent from '../../services/axios/api/event'
import { apiEvent } from '../../services/axios/api'

const getEvents = async () => {
  try {
    const { data, status } = await apiEvent.get()
    console.log(data);
  } catch (error) {
    const res = error.response
    console.log(res.status)
    console.log("error ", error.message)
  }
}
getEvents()
</script>
 
<template>
  <h1 class="text-3xl font-bold underline">
    Hello world!
  </h1>
</template>
 
<style>
</style>