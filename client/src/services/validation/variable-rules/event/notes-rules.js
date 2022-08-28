import { isBlank, isTrimmed, max } from '../../validator'

const label = 'Notes'
const field = {
  max: 500,
}

const name = (value = '') => {
  // if (!isTrimmed(value)) return `${label} must be a trimmed string`
  if (max(value.trim(), field.max)) return `${label} must be at most ${field.max} characters`
  return true
}
export default name
