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
            <h1 class="text-lg font-semibold">Cancel Event</h1>
        </template>
        <template #body>
            <p class="font-medium">Are you sure to cancel this event ?</p>
        </template>
        <template #footer>
            <div class="flex justify-end gap-2">
                <app-button
                    btn-type="secondary"
                    @click="modalState.onCancel"
                    ghost
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
