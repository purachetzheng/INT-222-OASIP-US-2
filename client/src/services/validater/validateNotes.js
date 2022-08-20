const validateNotes = (value) => {
  // if the field is not a valid email
  if(value && value.trim() === ''){
    return 'Notes must not be blank'
  }
  // All is good
  return true
}

export default validateNotes
