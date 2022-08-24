<script setup>
import { computed, onBeforeMount, provide, readonly, ref } from 'vue'
import { apiUser } from '../../../services/axios/api'
import UserTable from './UserTable.vue'
import UserDetailModal from './modal/UserDetailModal.vue'

const users = ref([])

const getUsers = async (page) => {
  try {
    const { data, status } = await apiUser.get()
    // const { content, number, totalPages } = data
    users.value = data
    // return data.content
  } catch (error) {
    // console.log(error)
    // console.log(error.response)
    const res = error.response
    console.log(res.status)
    console.log('error ', error.message)
  }
}

const deleteUsers = async (id) => {
  try {
    const { data, status } = await apiUser.delete(id)
    // const { content, number, totalPages } = data
    users.value = users.value.filter((u) => u.id != id)
    alert(`delete user: id`)
    // return data.content
  } catch (error) {
    // console.log(error)
    // console.log(error.response)
    const res = error.response
    console.log(res.status)
    console.log('error ', error.message)
  }
}

const viewTime = ref(false)

provide('viewTime', readonly(viewTime))

const [selectedUser, selectUser, resetSelectUser, showDetailModal] = [
  ref({}),
  (user) => (selectedUser.value = user),
  () => (selectedUser.value = {}),
  computed(() => Object.keys(selectedUser.value).length !== 0),
]

onBeforeMount(() => {
  getUsers()
})
const test = (e) => console.log(e)
</script>

<template>
  <main class="my-container h-full flex flex-col py-4 gap-0 justify-between">
    <header class="flex justify-between">
      <h1 class="text-2xl font-bold">User</h1>
      <div class="flex items-center gap-6">
        <div class="">
          <input type="checkbox" name="" v-model="viewTime" id="vt" />
          <label for="vt">view detail</label>
        </div>
        <fa-icon :icon="['fas', 'ellipsis']" class="fa-xl" />
        <button class="p-2 bg-blue-500">Add User</button>
      </div>
    </header>
    {{ selectedUser }}
    {{ showDetailModal }}
    <UserTable
      :users="users"
      @delete-user="deleteUsers"
      @view-detail="selectUser"
    />

    <ul class="grid grid-cols-12 gap-2 items-center">
      <li class="col-span-1">Id</li>
      <li class="col-span-3">Name</li>
      <li class="col-span-4">Email</li>
      <li class="col-span-1">Role</li>
      <li class="col-span-1">
        <button class="p-2 bg-blue-500" @click="viewTime = !viewTime">
          View Detail
        </button>
      </li>
    </ul>
    <ul class="flex flex-col gap-4">
      <li
        class="grid grid-cols-11 p-4 items-center gap-2 bg-white rounded-md"
        v-for="user in users"
      >
        <p class="col-span-5">
          {{ user.name }}
        </p>
        <p class="col-span-4 break-all">{{ user.email }}</p>
        <p class="col-span-1">{{ user.role }}</p>
        <div
          class="col-span-1 flex flex-wrap gap-2 items-center justify-center"
        >
          <fa-icon
            :icon="['far', 'pen-to-square']"
            class="cursor-pointer rounded-md p-2 bg-blue-500 text-white"
          />
          <fa-icon
            :icon="['far', 'trash-can']"
            class="cursor-pointer rounded-md p-2 bg-red-500 text-white"
            @click="deleteUsers(user.id)"
          />
        </div>
      </li>
    </ul>

    <UserDetailModal
      :show="showDetailModal"
      :user="selectedUser"
      @close="resetSelectUser"
    />
  </main>
</template>

<style></style>
