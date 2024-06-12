import axios from 'axios';

const signUpAPI = {
    async doSignUp(userData) {
        console.log('보내기 직전', userData);
        const url = 'http://13.125.49.195:8080/api/signup';
        return axios.post(url, userData);
    }
};
export default signUpAPI;
