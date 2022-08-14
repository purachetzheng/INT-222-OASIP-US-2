const validateNotes = (value) => {
  // if the field is not a valid email
  const regex = /^\s+$/
  if (regex.test(value)) {
    return 'This field must be a not blank'
  }
  // All is good
  return true
}

export default validateNotes
