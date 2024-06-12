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


        return axios.post(this.$serverUrl + '/api/login', reqData, {
            headers: {
                'Content-type': 'application/json'
            }
        });
    },

    async doLogout(){
        return axios.post(this.$serverUrl + '/api/logout');
    }
};

export default loginAPI;

