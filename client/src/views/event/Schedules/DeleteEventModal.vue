<script setup>
defineEmits([])
const props = defineProps({
    modalState: {
        type: Object,
    },
})
</script>

<template>
    <app-modal
        :show="modalState.state"
    >
        <template #header>
            <h1 class="text-lg font-semibold">Delete</h1>
        </template>
        <template #body>
            <p class="font-medium">Do you want to cancel this event?</p>
        </template>
        <template #footer>
            <div class="flex justify-end gap-2">
                <app-button
                    btn-type="outline-primary"
                    @click="modalState.onCancel"
                    >No</app-button
                >
                <app-button
                    btn-type="danger"
                    @click="modalState.onConfirm"
                    >Yes</app-button
                >
            </div>
        </template>
    </app-modal>
</template>

<style></style>
