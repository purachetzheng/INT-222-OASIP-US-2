const profilePlaceholder = (name = '') => {
    const split = name.split(' ')
    return split[0].charAt(0) + (split[1] ? split[1].charAt(0): '')
}
export default profilePlaceholder