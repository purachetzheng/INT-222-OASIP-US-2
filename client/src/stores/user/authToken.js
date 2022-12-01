export const getToken = () => localStorage.getItem('accessToken')

export const setToken = (token, type = 'oasip') => {
    localStorage.setItem('auth-with', type)
    localStorage.setItem('accessToken', token)
}
export const removeToken = () => {
    localStorage.removeItem('auth-with')
    localStorage.removeItem('accessToken')
}

