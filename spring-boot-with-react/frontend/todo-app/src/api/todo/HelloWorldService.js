import axios from "axios";

class HelloWorldService {

    excuteHelloWorldService() {
        return axios.get('http://localhost:8080/hello-world')
    }

    excuteHelloWorldBeanService() {
        return axios.get('http://localhost:8080/hello-world-bean')
    }


    excuteHelloWorldPathVariableService(name) {
        return axios.get(`http://localhost:8080/hello-world/path-variable/${name}`)
    }
}

export default new HelloWorldService()