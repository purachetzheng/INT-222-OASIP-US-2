<script setup>
const emits = defineEmits(['cancel', 'confirm', 'close'])
const props = defineProps({
    show: {
        type: Boolean,
        default: false,
    },
    type: {
        type: String,
        default: 'blank',
    },
    header: {
        type: String,
        default: 'default header',
    },
    desc: {
        type: String,
        default: 'default body',
    },
})
</script>

<template>
    <Teleport to="body">
        <Transition name="modal">
            <div v-if="show" class="modal-mask">
                <div class="modal-container p-6 bg-gray-50 rounded-md flex flex-col gap-4">
                    <div class="modal-header">
                        <slot name="header">
                            <p v-if="type === 'blank'" class="text-lg font-semibold">
                                {{ header }}
                            </p>
                            <div
                                v-if="type === 'confirm'"
                                class="p-4 bg-red-100 flex rounded-full w-min mx-auto"
                            >
                                <fa-icon
                                    :icon="['fas', 'triangle-exclamation']"
                                    class="text-red-500 fa-xl"
                                />
                            </div>
                        </slot>
                    </div>

                    <div class="modal-body">
                        <slot name="body">
                            <div v-if="type === 'confirm'" class="flex flex-col gap-2 max-w-xs">
                                <h1 class="text-lg font-semibold text-center">
                                    <slot name="title"> Cancel Event </slot>
                                </h1>
                                <p class="font-medium">
                                    <slot name="desc"> Are you sure to cancel this event? </slot>
                                </p>
                            </div>
                            <p v-else class="font-medium">{{ desc }}</p>
                        </slot>
                    </div>

                    <slot name="footer">
                        <div class="modal-footer" v-if="type === 'info'"></div>

                        <div v-if="type === 'confirm'" class="grid grid-cols-2 gap-2">
                            <app-button btn-type="secondary" ghost @click="$emit('cancel')"
                                >No</app-button
                            >
                            <app-button btn-type="danger" @click="$emit('confirm')">Yes</app-button>
                        </div>

                        <div class="modal-footer" v-else>
                            <div class="flex justify-center">
                                <app-button btn-type="success">Ok</app-button>
                            </div>
                        </div>
                    </slot>
                </div>
            </div>
        </Transition>
    </Teleport>
</template>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    transition: opacity 0.3s ease;
}

.modal-container {
    /* width: 300px; */
    /* width: 100%; */
    /* margin: 0px auto; */
    /* padding: 20px 30px; */
    background-color: #fff;
    /* border-radius: 2px; */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
}

/* .modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
} */

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter-from {
    opacity: 0;
}

.modal-leave-to {
    opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}
</style>
