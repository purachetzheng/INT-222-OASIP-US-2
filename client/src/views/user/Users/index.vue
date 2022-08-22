<script setup>
import { onBeforeMount, ref } from 'vue'
import { apiUser } from '../../../services/axios/api'

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
    users.value = users.value.filter(u => u.id!=id)
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

onBeforeMount(() => {
  getUsers()
})
</script>

<template>
  <main
    class="my-container h-full flex flex-col py-4 gap-4 justify-between"
  >
    <ul class="grid grid-cols-11 gap-2">
      <li class="col-span-5">Name</li>
      <li class="col-span-4">Email</li>
      <li class="col-span-1">Role</li>
      <li class="col-span-1"></li>
    </ul>
    <ul class="flex flex-col gap-4">
      <li
        class="grid grid-cols-11 p-4 items-center gap-2 bg-white rounded-md"
        v-for="user in users"
      >
        <p class="col-span-5">
          {{
            user.name
          }}0001-0002-0003-0004-0005-0006-0007-0008-0009-0010-0011-0012-0013-0014-0015-0016-0017-0018-0019-0020-
        </p>
        <p class="col-span-4">{{ user.email }}</p>
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
  </main>
</template>

<style></style>
