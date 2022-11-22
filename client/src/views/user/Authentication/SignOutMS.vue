<script setup>
import { useMsal } from '../../../services/MSAL/composition-api/useMsal'

defineEmits([])
const props = defineProps({
    //         first:{
    //         type:String,
    //         require:true,
    //    },
})

const { instance } = useMsal()

const logoutPopup = () => {
  instance.logoutPopup({
    mainWindowRedirectUri: "/"
  });
}

const logoutRedirect = () => {
  instance.logoutRedirect();
}
</script>

<template>
    <app-button
        btn-type="primary"
        @click="logoutPopup"
        btn-size="lg"
        :btn-icon="['fab', 'microsoft']"
    >
        Logout
    </app-button>
</template>

<style></style>
