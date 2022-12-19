import { object, string, number, mixed, ref } from 'yup'

const schema = object({
    name: string().trim().required().max(100).label('Name'),
    email: string().trim().required().max(50).label('Email'),
    role: string().required(),
    password: string().required().min(8).max(14).label('Password'),
    confirmPassword: string().oneOf([ref('password')], 'Passwords must match').required().label('Confirm Password'),
})
export default schema
