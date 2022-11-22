<script setup>
import { useMsal } from '../../../composition-api/useMsal'
import { loginRequest } from "../../../authConfig";
import { useUserStore } from '../../../stores';

defineEmits([])
const props = defineProps({
    //         first:{
    //         type:String,
    //         require:true,
    //    },
})
const userStore = useUserStore()

const { instance } = useMsal()

const loginPopup = async () => {
    const res = await instance.loginPopup(loginRequest)
    console.log(res);
    
}
const loginRedirect = () => {
    instance.loginRedirect(loginRequest)
}
</script>

<template>
    <app-button
        btn-type="primary"
        @click="userStore.signInMS"
        btn-size="lg"
        :btn-icon="['fab', 'microsoft']"
    >
        Sign in with Microsoft
    </app-button>
</template>

<style></style>
