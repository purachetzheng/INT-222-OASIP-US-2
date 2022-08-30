import { isBlank, isTrimmed, max } from '../../validator'

const label = 'Name'
const field = {
  max: 100,
}

const name = (value) => {
  if (isBlank(value)) return `${label} is a required field`
  value = value.trim()
  // if (!isTrimmed(value)) return `${label} must be a trimmed string`
  if (max(value, field.max)) return `${label} must be at most ${field.max} characters`
  return true
}
export default name
