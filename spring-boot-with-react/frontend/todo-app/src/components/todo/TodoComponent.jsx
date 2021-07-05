import { ErrorMessage, Field, Form, Formik } from 'formik'
import moment from 'moment'
import React, { Component } from 'react'
import TodoDataService from '../../api/todo/TodoDataService'
import AuthenticationService from './AuthenticatoinService'

class TodoComponent extends Component {

    constructor() {
        super()
        this.state = {
            todo: {
                id: -1,
                description: '',
                targetDate: moment(new Date()).format('YYYY-MM-DD')
            }
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    componentDidMount() {
        if (this.props.match.params.id == -1) {
            return
        }

        let username = AuthenticationService.getLoggedinUser()

        TodoDataService.retrieveTodo(username, this.props.match.params.id)
            .then(response => this.setState({
                todo: {
                    id: response.data.id,
                    description: response.data.description,
                    targetDate: moment(response.data.targetDate).format('YYYY-MM-DD')
                }
            }))
    }

    validate(values) {
        let errors = {}
        if (!values.description) {
            errors.description = 'Enter a Description'
        } else if (values.description.length < 5) {
            errors.description = 'Enter atleast 5 Characters in Description'
        }

        if (!moment(values.targetDate).isValid()) {
            errors.targetDate = 'Enter a valid Target Date'
        }

        return errors

    }

    onSubmit(values) {
        let username = AuthenticationService.getLoggedinUser()
       
        let todo = {
            id: this.state.todo.id,
            description: values.description,
            targetDate: values.targetDate
        }

        if (this.state.todo.id === -1) {
            TodoDataService.createTodo(username, todo)
                .then(() => this.props.history.push('/todos'))
        } else {
            TodoDataService.updateTodo(username, this.state.todo.id, todo)
                .then(() => this.props.history.push('/todos'))
        }
    }

    render() {
        let { description, targetDate } = this.state.todo

        return (
            <div>
                <h1>Todo</h1>
                <div className="container">
                    <Formik
                        initialValues={{ description, targetDate }}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="description" component="div"
                                        className="alert alert-warning" />
                                    <ErrorMessage name="targetDate" component="div"
                                        className="alert alert-warning" />
                                    <fieldset className="form-group">
                                        <label>Description</label>
                                        <Field className="form-control" type="text" name="description" />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Target Date</label>
                                        <Field className="form-control" type="date" name="targetDate" />
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }
}

export default TodoComponent
