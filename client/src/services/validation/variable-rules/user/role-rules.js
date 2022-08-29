import { isBlank, isTrimmed, max } from '../../validator'

const label = 'Role'


const name = (value) => {
  if (isBlank(value)) return `${label} is a required field`
  return true
}
export default name
