<script setup>
import PageWrapper from '../../components/Layout/PageWrapper.vue'
</script>

<template>
    <PageWrapper>
        <div class="flex flex-col gap-4">
            <div class="flex flex-col">


                <input type="date" name="" min="2022-12-20" id="">
                <div v-show="0" class="">
                    <!-- Primary -->
                    <button
                        class="px-4 py-2 bg-indigo-500 outline-none rounded text-white shadow-indigo-200 shadow-lg font-medium active:shadow-none active:scale-95 hover:bg-indigo-600 focus:bg-indigo-600 focus:ring-2 focus:ring-indigo-600 focus:ring-offset-2 disabled:bg-gray-400/80 disabled:shadow-none disabled:cursor-not-allowed transition-colors duration-200"
                    >
                        Primary
                    </button>

                    <!-- Secondary -->
                    <button
                        class="px-4 py-2 bg-indigo-50 outline-none border border-indigo-100 rounded text-indigo-500 font-medium active:scale-95 hover:bg-indigo-400 hover:text-white focus:ring-2 focus:ring-indigo-600 focus:ring-offset-2 disabled:bg-gray-400/80 disabled:shadow-none disabled:cursor-not-allowed transition-colors duration-200"
                    >
                        Secondary
                    </button>

                    <!-- Outline -->
                    <button
                        class="px-4 py-1.75 bg-transparent outline-none border-2 border-indigo-400 rounded text-indigo-500 font-medium active:scale-95 hover:bg-indigo-600 hover:text-white hover:border-transparent focus:bg-indigo-600 focus:text-white focus:border-transparent focus:ring-2 focus:ring-indigo-600 focus:ring-offset-2 disabled:bg-gray-400/80 disabled:shadow-none disabled:cursor-not-allowed transition-colors duration-200"
                    >
                        Secondary
                    </button>
                </div>
                <div class="">
                    <app-button btn-size="lg" btn-type="primary">Primary</app-button>
                    <app-button btn-size="lg" btn-type="primary" outline
                        >Outline Primary</app-button
                    >
                </div>
            </div>
            <p class="">font</p>
            <div class="flex gap-4">
                <p class="font-thin">md-thin</p>
                <p class="font-extralight">md-extralight</p>
                <p class="font-light">md-light</p>
                <p class="font-normal">md-normal</p>
                <p class="font-medium">md-medium</p>
                <p class="font-semibold">md-semibold</p>
                <p class="font-bold">md-bold</p>
                <p class="font-extrabold">md-extrabold</p>
            </div>
            <div class="flex gap-4">
                <p class="text-lg font-thin">lg-thin</p>
                <p class="text-lg font-extralight">lg-extralight</p>
                <p class="text-lg font-light">lg-light</p>
                <p class="text-lg font-normal">lg-normal</p>
                <p class="text-lg font-medium">lg-medium</p>
                <p class="text-lg font-semibold">lg-semibold</p>
                <p class="text-lg font-bold">lg-bold</p>
                <p class="text-lg font-extrabold">lg-extrabold</p>
            </div>
            <p class="">App Form</p>
            <div class="flex flex-col w-80">
                <app-vee-input type="text" name="text" />
                <app-vee-input type="number" name="number" />
                <app-vee-input type="date" name="date" />
                <app-vee-input type="time" name="time" />
                <app-vee-textarea />
            </div>
            <p class="">App Button</p>
            <div class="flex items-end gap-2">
                <!-- <app-button btn-size="xs">Button XS</app-button> -->
                <app-button btn-size="sm">Button SM</app-button>
                <app-button btn-size="sm" outline>Button SM</app-button>
                <app-button>Button MD</app-button>
                <app-button outline>Button MD</app-button>
                <app-button btn-size="lg">Button LG</app-button>
                <app-button btn-size="lg" outline>Button MD</app-button>
                <!-- <app-button btn-size="xl">Button XL</app-button>
                <app-button btn-size="xl" outline>Button XL</app-button> -->
            </div>
            <div class="flex items-end gap-2">
                <app-button btn-type="primary">Primary</app-button>
                <app-button btn-type="secondary">Secondary</app-button>
                <app-button btn-type="success">Success</app-button>
                <app-button btn-type="danger">Danger</app-button>
                <app-button btn-type="warning">Warning</app-button>
                <app-button btn-type="muted">Muted</app-button>
            </div>
            <div class="flex items-end gap-2">
                <app-button btn-type="primary" outline>Outline Primary</app-button>
                <app-button btn-type="secondary" outline>Outline Secondary</app-button>
                <app-button btn-type="success" outline>Outline Success</app-button>
                <app-button btn-type="danger" outline>Outline Danger</app-button>
                <app-button btn-type="warning" outline>Outline Warning</app-button>
                <app-button btn-type="muted" outline>Outline Muted</app-button>
            </div>
            <div class="flex items-end gap-2">
                <app-button btn-type="primary" ghost>Ghost Primary</app-button>
                <app-button btn-type="secondary" ghost>Ghost Secondary</app-button>
                <app-button btn-type="success" ghost>Ghost Success</app-button>
                <app-button btn-type="danger" ghost>Ghost Danger</app-button>
                <app-button btn-type="warning" ghost>Ghost Warning</app-button>
                <app-button btn-type="muted" ghost>Ghost Muted</app-button>
            </div>
        </div>
    </PageWrapper>
</template>

<style></style>
