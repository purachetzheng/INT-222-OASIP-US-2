import { isBlank, isEmail, isTrimmed, max } from '../../validator'

const label = 'Email'
const field = {
  max: 100,
}

const email = (value) => {
  if (isBlank(value)) return `${label} is a required field`
  value = value.trim()
  // if (!isTrimmed(value)) return `${label} must be a trimmed string`
  if (!isEmail(value)) return `${label} must be a valid email`
  if (max(value, field.max)) return `${label} must be at most ${field.max} characters`
  return true
}
export default email
