import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import AuthenticatoinService from './AuthenticatoinService.js'

class HeaderComponent extends Component {

    render() {
        const isUserLoggedin = AuthenticatoinService.isUserLoggedin()

        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div className="navbar-brand">Todo App</div>
                    <ul className="navbar-nav">
                        {isUserLoggedin &&
                            <>
                                <li><Link className="nav-link" to="/welcome">Home</Link></li>
                                <li><Link className="nav-link" to="/todos">Todos</Link></li>
                            </>
                        }
                    </ul>
                    <ul className="navbar-nav collapse navbar-collapse justify-content-end">
                        {!isUserLoggedin &&
                            <li><Link className="nav-link" to="/login">Login</Link></li>
                        }
                        {isUserLoggedin &&
                            <li><Link className="nav-link" to="/logout" onClick={AuthenticatoinService.logout}>Logout</Link></li>
                        }
                    </ul>
                </nav>
            </header>
        )
    }
}

export default HeaderComponent