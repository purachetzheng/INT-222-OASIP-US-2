// import { isBlank } from "../validator"
import emailRules from '../variable-rules/event/email-rules'
import nameRules from '../variable-rules/event/name-rules'
import datetimeRules from '../variable-rules/event/datetime-rules'
import notesRules from '../variable-rules/event/notes-rules'

const schema = {
  name: nameRules,
  email: emailRules,
  datetime: datetimeRules,
  notes: notesRules,

}

export default schema
