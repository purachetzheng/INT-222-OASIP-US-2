<script setup>
import PlaceholderAvatar from '../../../components/user/PlaceholderAvatar.vue'
import RoleBadge from '../../../components/user/RoleBadge.vue'
import UserList from './UserList.vue'
import ModalUserDetail from './ModalUserDetail.vue'
import ModalAddUser from './ModalAddUser.vue'
defineEmits(['view-detail', 'delete-user'])
const prop = defineProps({
    //         first:{
    //         type:String,
    //         require:true,
    //    },
})
const test = (v) => console.log(v);
</script>

<template>
    <UserList v-slot="{ users, updatedFormat, confirmDeleteModal, viewDetailModal, addUserModal }">
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

        <ModalUserDetail :viewDetailModal="viewDetailModal" />
        <ModalAddUser :addUserModal="addUserModal" />

        <header class="flex justify-between">
            <h1 class="text-2xl font-bold">User</h1>
            <div class="flex items-center gap-6">
                <app-button btn-type="primary" btn-size="sm" @click="addUserModal.show">
                    Add User
                </app-button>
            </div>
        </header>
        <table
            class="w-full text-sm text-left text-gray-500 dark:text-gray-400 border-separate border-spacing-y-2"
        >
            <thead class="text-sm text-gray-700 uppercase dark:text-gray-400">
                <tr>
                    <th scope="col" class="pb-1 px-6">Name</th>
                    <th scope="col" class="pb-1 px-6">Role</th>
                    <th scope="col" class="pb-1 px-6">Updated</th>
                    <th scope="col" class="pb-1 px-6">Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr
                    v-for="user in users"
                    class="p-2 border-b dark:border-gray-700 hover:bg-gray-50"
                >
                    <th
                        scope="row"
                        class="flex items-center py-4 px-6 text-gray-900 whitespace-nowrap bg-white rounded-l-lg"
                    >
                        <!-- <img class="w-10 h-10 rounded-full" src="/docs/images/people/profile-picture-1.jpg" > -->
                        <PlaceholderAvatar
                            class="avatar-small"
                            :name="user.name"
                            :role="user.role"
                        />
                        <div class="pl-3">
                            <div class="text-base font-semibold">{{ user.name }}</div>
                            <div class="text-gray-500 font-medium">
                                {{ user.email }}
                            </div>
                        </div>
                    </th>
                    <td class="py-4 px-6 bg-white">
                        <RoleBadge :title="user.role" />
                    </td>
                    <td class="py-4 px-6 bg-white">
                        <div class="flex items-center font-medium">
                            {{ updatedFormat(user.updatedOn) }}
                        </div>
                    </td>
                    <td class="py-4 px-6 bg-white rounded-r-lg">
                        <!-- <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit user</a> -->
                        <div class="flex gap-3 items-center">
                            <fa-icon
                                :icon="['far', 'eye']"
                                class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-blue-500 duration-100"
                                @click="viewDetailModal.show(user)"
                            />
                            <!-- <fa-icon
                                :icon="['far', 'pen-to-square']"
                                class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-purple-500 duration-100"
                            /> -->
                            <fa-icon
                                :icon="['far', 'trash-can']"
                                class="fa-lg cursor-pointer hover:scale-125 text-gray-700 hover:text-red-500 duration-100"
                                @click="confirmDeleteModal.show(user)"
                            />
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </UserList>
</template>

<style scoped></style>
