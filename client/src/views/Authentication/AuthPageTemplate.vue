<script setup>
import PageWrapper from '../../components/Layout/PageWrapper.vue'
const props = defineProps({
    imgSrc: {
        type: String,
        default: '@/assets/images/illustrations/error-illustration.svg',
    },
})
</script>

<template>
    <PageWrapper no-layout>
        <div class="h-full flex grow">
            <div class="hidden w-full lg:grid place-items-center">
                <div class="w-full max-w-xl p-6">
                    <slot name="img">
                        <img
                            src="../../assets/images/illustrations/error-illustration.svg"
                            alt=""
                        />
                    </slot>
                </div>
            </div>
            <div class="bg-white w-full lg:max-w-lg flex flex-col items-center">
                <div
                    class="flex flex-col justify-center max-w-md grow h-full gap-10 p-4"
                >
                    <div class="text-center">
                        <div class="text-2xl font-bold">Welcome Back</div>
                        <div class="font-medium text-gray-600">
                            Please sign in to continue
                        </div>
                    </div>

                    <div class="flex flex-col w-100">
                        <slot></slot>
                    </div>
                </div>
            </div>
        </div>
    </PageWrapper>
    <!-- <div class="container h-full mx-auto">
    <div class="block grid-cols-2 lg:grid gap-4">
        <div class="hidden flex-col lg:flex min-h-screen bg-red-200">
            <div class="my-auto">
                <div class="mx-20">
                    <img class="" src="../../../assets/images/illustrations/error-illustration.svg" alt="" />
                </div>
                
            </div>
            
        </div>
        <div class="bg-blue-200">
            hi
        </div>
    </div>
</div> -->
</template>

<style></style>
