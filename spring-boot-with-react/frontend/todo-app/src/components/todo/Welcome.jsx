import { useState } from "react";
import { Link } from "react-router-dom";
import HelloWorldService from "../../api/todo/HelloWorldService";

export default function Welcome() {

    const [message, setMessage] = useState();

    function retrieveWelcomeMessage() {
        // HelloWorldService.excuteHelloWorldService()
        //     .then(response => setMessage(response.data))
        //.catch()

        // HelloWorldService.excuteHelloWorldBeanService()
        // .then(response => setMessage(response.data.message))

        HelloWorldService.excuteHelloWorldPathVariableService("ramin")
            .then(response => setMessage(response.data.message))
            .catch(error => hanldeError(error))
    }

    function hanldeError(error) {
        console.log(error.response)

        let errorMessage = '';

        if (error.message)
            errorMessage += error.message

        if (error.response && error.response.data) {
            errorMessage += error.response.data.message
        }

        this.setState({ welcomeMessage: errorMessage })
    }

    return (
        <>
            <h1>Welcome!</h1>
            <div className="container">
                You can manage todos <Link to="/todos">here.</Link>
            </div>
            <hr />
            <div className="container">
                Click here to get welcome message
                <button
                    onClick={retrieveWelcomeMessage}
                    className="btn btn-success"
                >
                    Get welcome message
                </button>

                <div className="container">
                    {message}
                </div>
            </div>
        </>
    )
}