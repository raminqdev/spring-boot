import axios from "axios";
import { API_URL } from "../../Constants";

class TodoDataService {

    retrieveAllTodos(username) {
        return axios.get(`${API_URL}/users/${username}/todos`)
    }

    retrieveTodo(username, id) {
        return axios.get(`${API_URL}/users/${username}/todos/${id}`)
    }

    delteTodo(username, id) {
        return axios.delete(`${API_URL}/users/${username}/todos/${id}`)
    }

    createTodo(username, todo) {
        return axios.post(`${API_URL}/users/${username}/todos`, todo)
    }

    updateTodo(username, id, todo) {
        return axios.put(`${API_URL}/users/${username}/todos/${id}`, todo)
    }

}

export default new TodoDataService()