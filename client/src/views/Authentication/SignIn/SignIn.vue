<script setup>
import PageWrapper from '../../../components/Layout/PageWrapper.vue'
</script>

<template>
    <PageWrapper no-layout="">
        <div class="h-full flex grow">
            <div class="hidden w-full lg:grid place-items-center">
                <div class="w-full max-w-2xl p-6">
                    <img
                        src="../../../assets/images/illustrations/error-illustration.svg"
                        alt=""
                    />
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
                        <app-vee-input name="email" :placeholder="'Email'" />
                        <app-vee-input
                            name="password"
                            :placeholder="'Password'"
                        />

                        <app-button class="mt-2" btn-size="lg">
                            Sign In
                        </app-button>

                        <div class="text-center mt-4">
                            <div class="text-sm font-medium">
                                <span class="">Don't have Account? </span>
                                <router-link
                                    :to="{ name: 'Home' }"
                                    class="text-indigo-500 underline"
                                >
                                    Create account
                                </router-link>
                            </div>
                        </div>
                        <div class="flex my-6 items-center gap-4">
                            <div class="h-px flex-1 bg-slate-200 dark:bg-navy-500"></div>
                            <p>OR</p>
                            <div class="h-px flex-1 bg-slate-200 dark:bg-navy-500"></div>
                        </div>
                        <app-button
                            btn-type="outline-primary"
                            @click=""
                            btn-size="lg"
                            :btn-icon="['fab', 'microsoft']"
                        >
                            Sign in with Microsoft
                        </app-button>
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
