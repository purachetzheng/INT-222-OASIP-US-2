<script setup>
import PageWrapper from '../components/Layout/PageWrapper.vue'

const members = [
  { student_id: '63130500083', name: 'Purachet Zheng', role: 'Front End Developer', link: {github: 'https://github.com/purachetzheng'} },
  { student_id: '63130500094', name: 'Phumin Chumphu', role: 'Back End Developer', link: {github: 'https://github.com/ideastation-x'}  },
  { student_id: '63130500097', name: 'Yuttasart Prajaksuwan', role: 'Back End Developer', link: {github: 'https://github.com/oatyuthasart'}  },
]
</script>

<template>
  <PageWrapper>
    <div class="flex flex-col gap-8">
      <div class="bg-white rounded-lg shadow-xl min-h-[50px] h-60">
        <div class="p-10 h-full flex justify-center items-center gap-4">
          <p class="">
            Lorem ipsum, dolor sit amet consectetur adipisicing elit. Esse odio
            vero alias accusantium natus, facere, ab qui, molestiae soluta nobis
            optio magni quisquam sequi inventore nostrum minima similique
            recusandae? Nesciunt!
          </p>

          <img
            src="../assets/images/about-us/About-us.png"
            class="h-full"
            alt=""
          />
        </div>
      </div>
      <div class="bg-white rounded-lg shadow-xl min-h-[50px]">
        <div class="p-4 h-full">
          <h1 class="text-xl font-semibold text-center mb-4">Team Members</h1>
          <div class="grid grid-cols-3 gap-8 container mx-auto">
            <div class="flex flex-col items-center justify-center" v-for="member in members">
              <div
                class="w-40 h-40 bg-blue-200 rounded-full mb-4"
              ></div>
              <h5 class="text-xl font-semibold">{{ member.name }}</h5>
              <p class="">{{ member.role }}</p>
              <div class="flex gap-2 mt-2">
                <a :href="member.link.github" target="_blank">
                  <fa-icon :icon="['fab', 'github']" class="fa-xl hover:text-blue-700 duration-200" />
                </a>
                <a :href="member.link.github" target="_blank">
                  <fa-icon :icon="['fab', 'linkedin']" class="fa-xl hover:text-blue-700 duration-200" />
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </PageWrapper>
</template>

<style></style>
