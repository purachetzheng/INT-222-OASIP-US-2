<script setup>
import BaseModal from '../../../../components/base/BaseModal.vue'
import { formatDatetime } from '../../../../utils/dateTime'
import schema from '@/services/validation/schema/SignInUserSchema'
import InputField from '../../../../components/base/form/InputField.vue'
import { useForm, ErrorMessage, Field } from 'vee-validate'
import { onUpdated } from 'vue'
import RoleSelectField from '../components/RoleSelectField.vue'

const emits = defineEmits(['close', 'submit-form'])
const props = defineProps({
  show: {
    type: Boolean,
    default: false,
  },
})
const { handleSubmit, values, resetForm, meta, setFieldValue } = useForm({
  validationSchema: schema,
  initialValues: {
    name: '',
    email: '',
  },
})
const onSubmit = handleSubmit(({name = '', email = '', role}) => {
  const trimmedUser = {
    name: name.trim(),
    email: email.trim(),
    role: role
  }
  emits('submit-form', trimmedUser)
})
onUpdated(() => {
  if (props.show === true) {
    setTimeout(resetForm, 5)
  }
})


</script>

<template>
  <BaseModal :show="show" :width="'w-160'">
    <template #header>
      <h1 class="text-2xl font-semibold text-center">Add User</h1>
    </template>
    <template #body>
      <div class="flex flex-col gap-0">
        <InputField id="name" class="" name="name" type="text" :max="100" label="Name" />
        <InputField id="email" class="" name="email" type="text" :max="50" label="Email" />
        <!-- <p class="pt-2">Role</p> -->
        <RoleSelectField />
      </div>
    </template>

    <template #footer>
      <div class="flex gap-4 justify-start">
        <PrimaryButton type="submit" @click="onSubmit" class="btn btn-indigo duration-300 submit-btn" 
          :disabled="!meta.valid" >Create</PrimaryButton>
        <SecondaryButton type="button" class="btn btn-gray duration-300" @click="$emit('close')">
          Close
        </SecondaryButton>
        
      </div>
    </template>
  </BaseModal>
</template>

<style scoped>
</style>
