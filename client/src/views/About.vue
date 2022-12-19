<script setup>
import PageWrapper from "../components/Layout/PageWrapper.vue";

const members = [
    {
        student_id: "63130500083",
        name: "Purachet Zheng",
        role: "Front End Developer",
        link: { github: "https://github.com/purachetzheng" },
        bg: "bg-[url('../assets/images/about-us/63130500083.jpg')]",
    },
    {
        student_id: "63130500097",
        name: "Yuttasart Prajaksuwan",
        role: "Back End Developer",
        link: { github: "https://github.com/oatyuthasart" },
        bg: "bg-[url('../assets/images/about-us/63130500097.jpg')]",
    },
    {
        student_id: "63130500094",
        name: "Phumin Chumphu",
        role: "DevOps",
        link: { github: "https://github.com/ideastation-x" },
        bg: "bg-[url('../assets/images/about-us/63130500094.jpg')]",
    },
];
</script>

<template>
    <PageWrapper>
        <div class="flex flex-col gap-8">
            <div class="bg-white rounded-lg shadow-xl min-h-[50px]">
                <div class="p-10 h-full flex items-center gap-4">
                    <img src="../assets/images/about-us/about-us.svg" alt="" class="w-44" />
                    <div>
                        <div class="flex flex-col font-bold text-2xl text-indigo-500 mb-2">
                            OASIP-US2 | Online Appointment Scheduling System
                        </div>
                        We've created a online appointment scheduling system that can booking event, add new event for
                        student and lecturer role, for administration can manage users and check booking event from
                        student and lecturer.
                    </div>
                </div>
            </div>
            <div class="bg-white rounded-lg shadow-xl min-h-[50px]">
                <div class="p-4 h-full">
                    <h1 class="text-xl font-bold text-center mb-4 text-indigo-500">Team Members</h1>
                    <div class="grid grid-cols-3 gap-8 container mx-auto">
                        <div class="flex flex-col items-center justify-center" v-for="member in members">
                            <div class="w-40 h-40 bg-blue-200 rounded-full mb-4 bg-cover" :class="member.bg"></div>
                            <h5 class="text-xl font-semibold">{{ member.name }}</h5>
                            <p class="">{{ member.student_id }}</p>
                            <div class="flex gap-2 mt-2">
                                <a :href="member.link.github" target="_blank">
                                    <fa-icon :icon="['fab', 'github']" class="fa-xl hover:text-blue-700 duration-200" />
                                </a>
                                <a :href="member.link.github" target="_blank">
                                    <fa-icon
                                        :icon="['fab', 'linkedin']"
                                        class="fa-xl hover:text-blue-700 duration-200"
                                    />
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
