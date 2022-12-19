<script setup>
import { computed, onBeforeMount, provide, readonly, ref, reactive } from 'vue'
import { apiUser } from '../../../services/api/lib'
import { datetimeCalculate, datetimeCheck, formatDatetime } from '../../../utils/dateTime'
const users = ref([''])
const getUsers = async (page) => {
    try {
        const { data, status } = await apiUser.get()
        // const { content, number, totalPages } = data
        users.value = data
        console.log(data)
        // return data.content
    } catch (error) {
        console.log(error)
        // console.log(error.response)
        // const res = error.response
        // console.log(res.status)
        // console.log('error ', error.message)
    }
}
const createUser = async (user) => {
    try {
        const { data } = await apiUser.post(user)
        console.log(data)
        users.value.push(data)
        addUserModal.onSuccess()
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
const updateUser = async ({id, modifyUser, setErr}) => {
    try {
        const { data } = await apiUser.patch(id, modifyUser)
        users.value = users.value.map((user) => (user.id === data.id ? data : user))
        viewDetailModal.user = data
        viewDetailModal.onSuccess()
        
    } catch (error) {
        const { data, status } = error.response
        const { details } = data
        if (!details) return alert(status + ' error')
        details.forEach((details) => {
            setErr(details.field, details.errorMessage)
        })
    }
}

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
const addUserModal = reactive({
    state: false,
    // user: { id: null },
    show: (user) => {
        addUserModal.state = true
        // viewDetailModal.user = user
    },
    close: () => (addUserModal.state = false),
    onCreate: (data) => {
        createUser(data)
        
    },
    onCancel: () => {
        addUserModal.close()
    },
    onSuccess: () => {
        addUserModal.close()
        alert('created successful')
    }
    // editingOn: () => viewDetailModal.editingMode = true,
    // onEdit: (params) => updateUser(params),
    // onCancel: () => viewDetailModal.editingMode = false,
    // onSuccess: () => {
    //     viewDetailModal.editingMode = false
    //     alert('ok')
    // }
})


const viewDetailModal = reactive({
    state: false,
    user: { id: null },
    editingMode: false,
    show: (user) => {
        viewDetailModal.state = true
        viewDetailModal.user = user
    },
    close: () => (viewDetailModal.state = false),
    editingOn: () => viewDetailModal.editingMode = true,
    onEdit: (params) => updateUser(params),
    onCancel: () => viewDetailModal.editingMode = false,
    onSuccess: () => {
        viewDetailModal.close()
        alert('edited successful')
    }
})

const confirmDeleteModal = reactive({
    state: false,
    user: { id: null },
    show: (user) => {
        confirmDeleteModal.state = true
        confirmDeleteModal.user = user
    },
    close: () => (confirmDeleteModal.state = false),
    onConfirm: () => {
        console.log(confirmDeleteModal.user);
        deleteUser(confirmDeleteModal.user.id)
        confirmDeleteModal.close()
    },
    onCancel: () => {
        confirmDeleteModal.close()
    },
})


onBeforeMount(() => {
    getUsers()
})
const disPlayUpdated = (datetime) => {
    // if (datetimeCheck.isHourAgo(datetime)) return datetimeCalculate.timeFromNow(datetime)
    if (datetimeCheck.isToday(datetime)) return datetimeCalculate.timeFromNow(datetime)
    if (datetimeCheck.isYesterday(datetime)) return 'Yesterday'
    return formatDatetime.dayMonthYear(datetime)
}
</script>

<template>
    <div class="">
        <slot
            :users="users"
            :updatedFormat="disPlayUpdated"
            :confirmDeleteModal="confirmDeleteModal"
            :viewDetailModal="viewDetailModal"
            :addUserModal="addUserModal"
        ></slot>
    </div>
</template>

<style></style>
