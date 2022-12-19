<script setup>
import { computed, onBeforeMount, provide, readonly, ref, reactive } from 'vue'
import { apiUser } from '../../../services/api/lib'
import UserTable from './UserTable.vue'
import UserDetailModal from './modal/UserDetailModal.vue'
import AddUserModal from './modal/AddUserModal.vue'
import PageWrapper from '../../../components/Layout/PageWrapper.vue'
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
    if (confirm('Do you really want to delete?')) deleteUser(id)
}

const confirmDeleteModal = reactive({
    state: false,
    user: { id: null },
    show: (user) => {
        confirmDeleteModal.state = true
        confirmDeleteModal.user = user
    },
    close: () => (confirmDeleteModal.state = false),
    onConfirm: () => {
        deleteUser(confirmDeleteModal.user.id)
        confirmDeleteModal.close()
    },
    onCancel: () => {
        confirmDeleteModal.close()
    },
})

const deleteUser = async (id) => {
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
        users.value = users.value.map((user) => (user.id === data.id ? data : user))
        selectedUser.value = data
        alert('ok')
    } catch (error) {
        const { data, status } = error.response
        const { details } = data
        if (!details) return alert(status + ' error')
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
        details.forEach((details) => {
            messageError += `${details.field}: ${details.errorMessage}\n`
        })
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
        <app-modal
            :show="confirmDeleteModal.state"
            type="confirm"
            @cancel="confirmDeleteModal.onCancel"
            @confirm="confirmDeleteModal.onConfirm"
        >
            <template #title> Delete User </template>
            <template #desc>
                Are you sure to delete
                <span class="italic font-semibold">"{{ confirmDeleteModal.user.name }}"</span> user?
            </template>
        </app-modal>
        <main class="flex flex-col gap-0 justify-between">
            <header class="flex justify-between">
                <h1 class="text-2xl font-bold">User</h1>
                <div class="flex items-center gap-6">
                    <fa-icon :icon="['fas', 'ellipsis']" class="fa-xl" />
                    <app-button btn-type="primary" btn-size="md" @click="openAddUserModal"
                        >Add User</app-button
                    >
                </div>
            </header>

            <UserTable
                :users="users"
                @delete-user="confirmDeleteModal.show"
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
            <AddUserModal
                :show="showAddUserModal"
                @close="closeAddUserModal"
                @submit-form="onSubmit"
            />
        </main>
    </PageWrapper>
</template>

<style></style>
