<script setup>
/*Copyright 2022 Purachet Zheng
*
*
*/
import { computed, onBeforeMount, ref } from 'vue';
import { onBeforeRouteUpdate, useRoute } from 'vue-router';
import {IcArrowRight, IcArrowLeft} from '../../../assets/icons/arrows-icons';
const { path } = useRoute()
defineEmits(['emitToPage', 'emitNextPage', 'emitPrevPage'])
const props = defineProps({
    total: {
        type: Number,
        require: true,
    },
    page: {
        type: Number,
        require: true,
    },
    firstPage:{
        type: Number,
        default: 1
    }
})
const paginationList = ref([])

//Copyright 2022 Purachet Zheng
// const genPagination = () => {
//     const total = props.total
//     const page = props.page
//     if(total <= 7) return total
//     if(page <= 4) return [1,2,3,4,5,'...',total]
//     if(page >= total-3) return [1,'...',total-4,total-3,total-2,total-1,total]
//     return [1,'...',page-1,page,page+1,'...',total]
// }
const genPagination = computed(()=>{
    const total = props.total
    const page = props.page
    if(total <= 7) return total
    if(page <= 4) return [1,2,3,4,5,'...',total]
    if(page >= total-3) return [1,'...',total-4,total-3,total-2,total-1,total]
    return [1,'...',page-1,page,page+1,'...',total]
})
// 

// onBeforeMount(()=>{
//     paginationList.value = genPagination()
//     console.log(genPagination());
// })
// onBeforeRouteUpdate(async (to, from) =>{
//     if(to.query.page !== from.query.page){
//         paginationList.value = genPagination()
//     }
// })
</script>
 
<template>
    <div class="">
        <div class="flex gap-8 justify-center items-center">
            <button :class="['rounded-full bg-white shadow-all', 'transition ease-out duration-300',
                'enabled:scale-175 enabled:hover:bg-gray-100']"
                @click="$emit('emitPrevPage')"
                :disabled="page == 0">
                <IcArrowLeft class="w-6 h-6 text-gray-600" />
            </button>
            <!-- <router-link :to="{ name: 'ProductList', query:{ page: num } }" v-for="num in total" class="text-lg bg-red-400 p-2">
                {{ num }}
            </router-link> -->
            <button v-for="num in genPagination" 
                :class="['rounded-full w-10 h-10 text-lg font-semibold', 
                    num !== '...'? 'disabled:bg-blue-500 disabled:text-white hover:text-blue-500' :'']"
                @click="$emit('emitToPage', num-1)"
                :disabled="1+page == num|| num === '...'">
                {{ num }}
            </button>
            <button :class="['rounded-full bg-white shadow-all', 'transition ease-out duration-300',
                'enabled:scale-175 enabled:hover:bg-gray-100']"
                @click="$emit('emitNextPage')"
                :disabled="page == total-1"> 
                <IcArrowRight class="w-6 h-6 text-gray-600" />
            </button>
        </div>
    </div>
</template>
 
<style scoped>
.shadow-all{
    -moz-box-shadow: 0 0 6px rgba(0,0,0,0.15);
    -webkit-box-shadow: 0 0 6px rgba(0,0,0,0.15);
    box-shadow: 0 0 6px rgba(0,0,0,0.15);
}
</style>