// src/service/loginAPI.js
import axios from 'axios'
import { mapState } from 'vuex'
import store from "@/Vuex/store";

const loginAPI = {
    async doLogin(LoginData) {
        const reqData = {
            'id': LoginData.userId,
            'password': LoginData.userPw
        };
        const serverUrl = 'http://13.125.49.195:8080';

        return axios.post(serverUrl + '/api/login', reqData, {
            headers: {
                'Content-type': 'application/json'
            }
        });
    }
};

export default loginAPI;

