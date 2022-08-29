import { isBlank, isTrimmed, max, min } from '../../validator'

const label = 'Password'
const field = {
  max: 14,
  min: 8,
}

const password = (value) => {
  if (isBlank(value)) return `${label} is a required field`
  // if (!isTrimmed(value)) return `${label} must be a trimmed string`
  if (min(value, field.min))
    return `${label} must be at less ${field.min} characters`
  if (max(value, field.max))
    return `${label} must be at most ${field.max} characters`
  return true
}
export default password
