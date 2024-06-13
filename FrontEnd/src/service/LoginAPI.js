// src/service/loginAPI.js
import axios from 'axios'
import { mapState } from 'vuex'
import store from "@/Vuex/store";

const serverUrl = 'http://13.125.49.195:8080';

const loginAPI = {
    async doLogin(LoginData) {
        const reqData = {
            'id': LoginData.userId,
            'password': LoginData.userPw
        };


        return axios.post(serverUrl + '/api/login', reqData, {
            headers: {
                'Content-type': 'application/json'
            }
        });
    },

    async doLogout(){
        return axios.post(serverUrl + '/api/logout');
    }
};

export default loginAPI;

