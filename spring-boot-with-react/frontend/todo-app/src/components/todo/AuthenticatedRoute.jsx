import React, { Component } from 'react'
import { Redirect, Route } from 'react-router-dom'
import AuthenticatoinService from './AuthenticatoinService.js'

class AuthenticatedRoute extends Component {
    render() {
        if (AuthenticatoinService.isUserLoggedin()) {
            return <Route {...this.props} />
        } else {
            return <Redirect to="/login" />
        }
    }
}

export default AuthenticatedRoute
