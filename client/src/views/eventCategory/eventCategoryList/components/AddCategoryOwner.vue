<script setup>
import { ref, onBeforeMount, computed } from 'vue';
import { apiUser } from '../../../../services/api/lib';

defineEmits([])
const prop = defineProps({
        first:{
        type:String,
        require:true,
   },
})

const users = ref([])

const lecturers = computed(()=>users.value.filter(user => user.role == 'lecturer'))
const selectedUser = ref(0)


const getUsers = async (page) => {
    try {
        const { data, status } = await apiUser.get()
        // const { content, number, totalPages } = data
        users.value = data
        // return data.content
    } catch (error) {
        console.log(error)
        // console.log(error.response)
        // const res = error.response
        // console.log(res.status)
        // console.log('error ', error.message)
    }
}


onBeforeMount(() => {
    getUsers()
})
</script>
 
<template>
    <div class="flex">
        <select id="cars" name="cars" v-model="selectedUser">
            <option v-for="lecturer in lecturers" :value="lecturer.id">{{lecturer.name}}</option>
        </select>
        {{selectedUser}}
        <app-button btn-type="primary" >Add</app-button>
    </div>
</template>
 
<style>

</style>