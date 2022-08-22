import { isBlank, isEmail, isTrimmed, max } from '../../validator'

const label = 'Datetime'
const field = {
  max: 100,
}

const datetime = ({date, time}) => {
  // const datetime = date + 'T' + time
  if (isBlank(date)||isBlank(time)) return `${label} is a required field`

  return true
}
export default datetime
