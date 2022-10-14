<script setup>
import { computed, onBeforeMount, provide, readonly, ref } from 'vue'
import { apiUser } from '../../../services/api/lib'
import UserTable from './UserTable.vue'
import UserDetailModal from './modal/UserDetailModal.vue'
import AddUserModal from './modal/AddUserModal.vue';
import PageWrapper from '../../../components/Layout/PageWrapper.vue';
const users = ref([])

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

const askForDelete = (id) => {
  if(confirm('Do you really want to delete?')) deleteUsers(id)
}
const deleteUsers = async (id) => {
  try {
    const { data, status } = await apiUser.delete(id)
    // const { content, number, totalPages } = data
    users.value = users.value.filter((u) => u.id != id)
    alert(`successfully deleted`)
    
    // return data.content
  } catch (error) {
    // console.log(error)
    // console.log(error.response)
    const res = error.response
    console.log(res.status)
    console.log('error ', error.message)
  }
}

const updateUsers = async (id, modifyUser, setFieldError) => {
  try {
    const { data } = await apiUser.patch(id, modifyUser)
    users.value = users.value.map((user) => user.id === data.id ? data : user)
    selectedUser.value = data
    alert('ok')
  } catch (error) {
    const { data, status } = error.response
    const { details } = data
    if(!details) return alert(status + ' error')
    details.forEach((details) => {
      setFieldError(details.field, details.errorMessage)
    })
  }
}

const onSubmit = async (user) => {
  try {
    const { data } = await apiUser.post(user)
    console.log(data)
    users.value.push(data)
    closeAddUserModal()
    alert('ok')
  } catch (error) {
    console.log(error)
    const { data, status } = error.response
    const { details } = data
    let messageError = ''
    details.forEach(details => {
        messageError+=`${details.field}: ${details.errorMessage}\n`
    });
    alert(messageError)
  }
}

const [selectedUser, selectUser, resetSelectUser, showDetailModal] = [
  ref({}),
  (user) => (selectedUser.value = user),
  () => (selectedUser.value = {}),
  computed(() => Object.keys(selectedUser.value).length !== 0),
]

const [showAddUserModal, openAddUserModal, closeAddUserModal] = [
  ref(false),
  () => (showAddUserModal.value = true),
  () => (showAddUserModal.value = false),
]

onBeforeMount(() => {
  getUsers()
})
</script>

<template>
  <PageWrapper>
  <main class="flex flex-col  gap-0 justify-between">
    <header class="flex justify-between">
      <h1 class="text-2xl font-bold">User</h1>
      <div class="flex items-center gap-6">
        <fa-icon :icon="['fas', 'ellipsis']" class="fa-xl" />
        <button class="p-2 bg-blue-500" @click="openAddUserModal">Add User</button>
      </div>
    </header>

    <UserTable
      :users="users"
      @delete-user="askForDelete"
      @view-detail="selectUser"
      class="grow overflow-hidden"
    />

    <div class="">Foot</div>

    <UserDetailModal
      :show="showDetailModal"
      :user="selectedUser"
      @update-user="updateUsers"
      @close="resetSelectUser"
    />
    <AddUserModal :show="showAddUserModal" @close="closeAddUserModal" @submit-form="onSubmit" />
  </main>
</PageWrapper>
</template>

<style></style>
