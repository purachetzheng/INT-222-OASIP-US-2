import apiClient from "./apiClient";

export const signInUser = async (user) => {
    try {
      const { data } = apiClient.post(`/api/users/login`, user)
      localStorage.setItem('jwt', data.token);
      localStorage.setItem('refreshToken', data.refreshToken);
      alert('Password Matched')
      // myUser.loginUser()
      userStore.getUserInfo()
    } catch (error) {
      const { data, status } = error.response
      const { details, message } = data
      if(status === 401) setFieldError('password', message)
      if(status === 404) setFieldError('email', message)
    }
}