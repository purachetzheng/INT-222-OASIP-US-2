import emailRules from '../variable-rules/user/email-rules'
import nameRules from '../variable-rules/user/name-rules'
import roleRules from '../variable-rules/user/role-rules'

const schema = {
  name: nameRules,
  email: emailRules,
  role: roleRules,
}

export default schema
