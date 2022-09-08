<script setup>
import BaseModal from '../../../../components/base/BaseModal.vue'
import { formatDatetime } from '../../../../utils/dateTime'
import { roleProfile } from '@/config'
import profilePlaceholder from '@/utils/profilePlaceholder'
import { computed, onUpdated, ref } from 'vue'
import { useForm, ErrorMessage, Field } from 'vee-validate'
import InputField from '../../../../components/base/form/InputField.vue'
import schema from '@/services/validation/schema/EditUserSchema'
import RoleSelectField from '../../../../components/user/RoleSelectField.vue'
import PlaceholderAvatar from '../../../../components/user/PlaceholderAvatar.vue'
const emits = defineEmits(['close', 'update-user'])
const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  user: {
    type: Object,
    require: true,
  },
})
const user = computed(() => props.user)

const [editingMode, editingOn, editingOff] = [
  ref(false),
  () => {
    editingMode.value = true
    setValues({
      name: user.value.name,
      email: user.value.email,
      role: user.value.role,
    })
  },
  () => (editingMode.value = false),
]

const {
  handleSubmit,
  values,
  resetForm,
  meta,
  setValues,
  setFieldError,
} = useForm({
  validationSchema: schema,
  validateOnMount: false,
})

const onSubmitEdit = handleSubmit(async ({ name = '', email = '', role } = {}) => {
  const editUser = {}
  name = name.trim()
  email = email.trim()
  const isNameChanged = user.value.name !== name
  const isEmailChanged = user.value.email !== email
  const isRoleChanged = user.value.role !== role

  if (isNameChanged) editUser.name = name
  if (isEmailChanged) editUser.email = email
  if (isRoleChanged) editUser.role = role

  const isUserChanged = Object.keys(editUser).length !== 0

  if (!isUserChanged) return alert('ok')
  emits('update-user', user.value.id, editUser, setFieldError)
})
onUpdated(() => {
  if (props.show === true) {
  }
  if (props.show !== true) {
    editingOff()
  }
})
</script>

<template>
  <BaseModal
    v-if="!editingMode"
    :show="show"
    @close="$emit('closeModal')"
    :width="'w-100'"
  >
    <template #header>
      <h1 class="text-2xl font-semibold text-center">{{ user.name }}</h1>
    </template>

    <template #body>
      <div class="flex flex-col items-center gap-4">
        <div class="flex gap-8">
          <PlaceholderAvatar class="avatar-medium" :name="user.name" :role="user.role" />
          <div class="flex flex-col items-start justify-center">
            <p class="text-sm text-gray-600">{{ user.email }}</p>
            <span
              class="px-2 py-1 rounded-lg mt-2"
              :class="roleProfile[user.role].badges"
            >
              <fa-icon :icon="['fas', 'circle']" class="fa-2xs" />
              {{ user.role }}
            </span>
          </div>
        </div>
        <div class="flex gap-4">
          <div class="">
            <p class="text-center text-sm text-indigo-500 font-medium">
              Created Date:
            </p>
            <p class="text-sm text-gray-600 font-medium">
              {{ formatDatetime.monthDayYearHourTime(user.createdOn) }}
            </p>
          </div>
          <div class="">
            <p class="text-center text-sm text-indigo-500 font-medium">
              Updated Date:
            </p>
            <p class="text-sm text-gray-600 font-medium">
              {{ formatDatetime.monthDayYearHourTime(user.updatedOn) }}
            </p>
          </div>
        </div>
      </div>
    </template>

    <template #footer>
      <div class="flex justify-center gap-4">
        <PrimaryButton
          type="submit"
          class="btn btn-indigo duration-300 submit-btn"
          :disabled="0"
          @click="editingOn"
          >Edit</PrimaryButton
        >
        <SecondaryButton
          type="button"
          class="btn btn-gray duration-300"
          @click="$emit('close')"
        >
          Close
        </SecondaryButton>
      </div>
    </template>
  </BaseModal>
  <BaseModal v-else :show="show" @close="$emit('closeModal')" :width="'w-128'">
    <template #header>
      <h1 class="text-2xl font-semibold text-center">Edit</h1>
    </template>

    <template #body>
      <div class="flex flex-col">
        <InputField
          id="name"
          class=""
          name="name"
          type="text"
          :max="100"
          label="Name"
        />
        <InputField
          id="email"
          class=""
          name="email"
          type="text"
          :max="50"
          label="Email"
        />
        <RoleSelectField />
      </div>
    </template>

    <template #footer>
      <div class="flex justify-center gap-4">
        <PrimaryButton
          class="btn btn-indigo duration-300 submit-btn"
          @click="onSubmitEdit"
          :disabled="!meta.valid"
        >
          Save
        </PrimaryButton>
        <SecondaryButton
          type="button"
          class="btn btn-gray duration-300"
          @click="editingOff"
        >
          Cancel
        </SecondaryButton>
      </div>
    </template>
  </BaseModal>
</template>

<style scoped>
.card {
  @apply flex flex-col sm:flex-row  gap-4 justify-center items-center p-6 text-gray-500 bg-white rounded-lg border-2 border-gray-200 cursor-pointer hover:bg-gray-100 hover:text-gray-600;
}
</style>
