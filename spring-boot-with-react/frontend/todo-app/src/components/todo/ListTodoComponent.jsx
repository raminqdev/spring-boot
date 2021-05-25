import moment from 'moment'
import React, { Component } from 'react'
import TodoDataService from '../../api/todo/TodoDataService'
import AuthenticationService from './AuthenticatoinService'

class ListTodoComponent extends Component {

    constructor() {
        super()
        this.state = {
            todos: [],
            message: null
        }
        this.deleteTodoClicked = this.deleteTodoClicked.bind(this)
        this.refreshTodos = this.refreshTodos.bind(this)
        this.updateTodoClicked = this.updateTodoClicked.bind(this)
        this.addTodoClicked = this.addTodoClicked.bind(this)
    }

    componentDidMount() {
        this.refreshTodos()
    }

    deleteTodoClicked(id) {
        let username = AuthenticationService.getLoggedinUser()
        TodoDataService.delteTodo(username, id)
            .then(response => {
                this.setState({ message: `Delete of todo ${id} successful` })
                this.refreshTodos()
            })
    }

    updateTodoClicked(id) {
        this.props.history.push(`/todos/${id}`)
    }

    addTodoClicked() {
        this.props.history.push(`/todos/-1`)
    }

    refreshTodos() {
        let username = AuthenticationService.getLoggedinUser()
        TodoDataService.retrieveAllTodos(username)
            .then(response => this.setState({ todos: response.data }))
    }

    render() {
        return (
            <div>
                <h1>List Todos</h1>
                {this.state.message &&
                    <div className="alert alert-success">{this.state.message}</div>
                }
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>description</th>
                                <th>Is Completed</th>
                                <th>Target Date</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.todos.map(todo =>
                                    <tr key={todo.id}>
                                        <td>{todo.description}</td>
                                        <td>{todo.done.toString()}</td>
                                        <td>{moment(todo.targetDate.toString()).format('YYYY-MM-DD')}</td>
                                        <td>
                                            <button className="btn btn-success" onClick={() => this.updateTodoClicked(todo.id)}>
                                                Update
                                            </button>
                                        </td>
                                        <td>
                                            <button className="btn btn-warning" onClick={() => this.deleteTodoClicked(todo.id)}>
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                    <div className='row'>
                        <button className="btn btn-success" onClick={this.addTodoClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default ListTodoComponent