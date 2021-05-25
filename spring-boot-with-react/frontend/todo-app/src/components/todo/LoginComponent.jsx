import React, { Component } from 'react'
import AuthenticatoinService from './AuthenticatoinService.js'

class LoginComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            hasLoginFailed: false,
            showSuccessMessage: false
        }
        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.value })
    }

    // loginClicked() {
    //     AuthenticatoinService.executeBasicAuthenticationService(this.state.username, this.state.password)
    //         .then(() => {
    //             AuthenticatoinService.registerSuccessfulLogin(this.state.username, this.state.password)
    //             this.props.history.push("/welcome")
    //         })
    //         .catch(() => {
    //             this.setState({ showSuccessMessage: false })
    //             this.setState({ hasLoginFailed: true })
    //         })
    // }

    loginClicked() {
        AuthenticatoinService.executeJwtAuthenticationService(this.state.username, this.state.password)
            .then((response) => {
                AuthenticatoinService.registerSuccessfulLoginForJwt(this.state.username, response.data.token)
                this.props.history.push("/welcome")
            })
            .catch(() => {
                this.setState({ showSuccessMessage: false })
                this.setState({ hasLoginFailed: true })
            })
    }

    render() {
        return (
            <div>
                <h1>Login</h1>
                <div className="container">
                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                    {this.state.showSuccessMessage && <div>Login Sucessful</div>}
                    Username: <input type="text" name="username"
                        value={this.state.username}
                        onChange={this.handleChange}
                    />
                    Password: <input type="password" name="password"
                        value={this.state.password}
                        onChange={this.handleChange}
                    />
                    <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                </div>
            </div>
        )
    }
}

export default LoginComponent