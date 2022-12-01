export const getToken = () => localStorage.getItem('accessToken')

export const setToken = (token, type) => {
    localStorage.setItem('accessToken', token)
    if(type) localStorage.setItem('auth-with', type)
}
export const deleteToken = () => {
    localStorage.removeItem('auth-with')
    localStorage.removeItem('accessToken')
}

