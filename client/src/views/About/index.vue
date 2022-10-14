<script setup>
import PageWrapper from '../../components/Layout/PageWrapper.vue';

    
const members = [
    { student_id: '63130500083', name: 'Purachet Zheng' },
    { student_id: '63130500094', name: 'Phumin Chumphu' },
    { student_id: '63130500097', name: 'Yuttasart Prajaksuwan' },
]
</script>

<template>
  <PageWrapper>
  <div
    class=" flex flex-col gap-4 items-center"
  >
    <h1 class="text-3xl font-bold">INT222 - US2</h1>
    <div class="grid grid-cols-3 container">
        <div class="flex flex-col items-center" v-for="member in members">
            <!-- <div class="w-60 h-60 bg-white"></div> -->
            <h5 class="text-lg font-semibold">{{ member.name }}</h5>
            <p class="">{{ member.student_id}}</p>
        </div>
    </div>
  </div>
</PageWrapper>
</template>

<style>

</style>
