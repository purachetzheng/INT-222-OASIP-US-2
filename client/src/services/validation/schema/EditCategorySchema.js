import { object, string, number} from 'yup'

const schema = object({
  name: string().required().max(100).trim().label('Name'),
  duration: number().required().integer().min(1).max(480).label('Duration'),
  description: string().notRequired().max(500).trim().label('Description'),
})
export default schema
