<script setup>
import PageWrapper from '../../components/Layout/PageWrapper.vue'


</script>

<template>
  <PageWrapper>
    <div class="">
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
            <app-button>Button MD</app-button>
            <app-button btn-size="lg">Button SM</app-button>
            <app-button btn-size="xl">Button SM</app-button>
        </div>
        <div class="mt-4 flex items-end gap-2">
            <app-button btn-size="lg" btn-type="primary">Primary</app-button>
            <app-button btn-size="lg" btn-type="secondary">Secondary</app-button>
            <app-button btn-size="lg" btn-type="success">Success</app-button>
            <app-button btn-size="lg" btn-type="danger">Danger</app-button>
            <app-button btn-size="lg" btn-type="warning">Warning</app-button>
            <app-button btn-size="lg" btn-type="muted">Muted</app-button>
        </div>
        <div class="mt-4 flex items-end gap-2">
            <app-button btn-size="lg" btn-type="outline-primary">Outline Primary</app-button>
            <app-button btn-size="lg" btn-type="outline-secondary">Outline Secondary</app-button>
            <app-button btn-size="lg" btn-type="outline-success">Outline Success</app-button>
            <app-button btn-size="lg" btn-type="outline-danger">Outline Danger</app-button>
            <app-button btn-size="lg" btn-type="outline-warning">Outline Warning</app-button>
            <app-button btn-size="lg" btn-type="outline-muted">Outline Muted</app-button>
        </div>
    </div>
  </PageWrapper>
</template>

<style></style>
