import apiClient from "../../apiClient";

const apiEvent = {
    get(){
        return apiClient.get('/events');
    }
}

export default apiEvent