import axios from "axios"
import { API_URL } from "../../Constants"

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

class AuthenticationService {

    // executeBasicAuthenticationService(username, password) {
    //     return axios.get(`http://localhost:8080/basicauth`, { headers: { authorization: this.createBasicAuthToken(username, password) } })
    // }

    executeJwtAuthenticationService(username, password) {
        return axios.post(`${API_URL}/authenticate`, { username, password })
    }

    registerSuccessfulLoginForJwt(username, token) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        this.setupAxiosInterceptors(this.createJwtAuthToken(token))
    }

    // registerSuccessfulLogin(username, password) {
    //     sessionStorage.setItem('authenticatedUser', username)
    //     this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
    // }

    logout() {
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    }

    isUserLoggedin() {
        let username = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (username === null) return false
        return true
    }

    getLoggedinUser() {
        let username = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (username === null) return ''
        return username
    }

    // createBasicAuthToken(username, password) {
    //     return 'Basic ' + window.btoa(username + ":" + password) //base64 encoding
    // }

    createJwtAuthToken(token) {
        return 'Bearer ' + token
    }

    setupAxiosInterceptors(bearToken) {
        axios.interceptors.request.use(
            (config) => {
                if (this.isUserLoggedin()) {
                    config.headers.authorization = bearToken
                }
                return config
            }
        )
    }
}

export default new AuthenticationService()