// import { isBlank } from "../validator"
// import emailRules from '../variable-rules/event/email-rules'
// import nameRules from '../variable-rules/event/name-rules'
// import datetimeRules from '../variable-rules/event/datetime-rules'
// import notesRules from '../variable-rules/event/notes-rules'
import { object, string, number, date, mixed} from 'yup'
// const schema = {
//   name: nameRules,
//   email: emailRules,
//   datetime: datetimeRules,
//   // date: ,
//   // time: ,
//   notes: notesRules,

// }

const schema = object().shape({
  name: string().required().max(100).trim().label('Name'),
  email: string().required().max(100).trim().label('Email'),
  // date: string().required().max(100).trim().label('Name'),
  // time: string().required().max(100).trim().label('Name'),
  notes: string().max(500).trim().label('Note'),
  datetime: object().shape({
    date: string().required().label('Date'),
    time: string().required().label('Time'),
  })
})

export default schema
