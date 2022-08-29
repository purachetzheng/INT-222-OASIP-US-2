// import isOverlap from './time/isOverlap'
export // isOverlap,
 {}
export const isTrimmed = (value) => {
  const regex = /^\s+|\s+$/
  return !regex.test(value)
}

export const isBlank = (value) => {
  return !value
}

export const max = (value = '', size) => {
  return value.length > size
}

export const min = (value = '', size) => {
  return value.length < size
}

export const isEmail = (value) => {
  const regex =
    /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
  return regex.test(value)
}
