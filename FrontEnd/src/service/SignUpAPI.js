import axios from 'axios';

const signUpAPI = {
    async doSignUp(userData) {
        console.log('보내기 직전', userData);
        return axios.post(this.$serverUrl +`/api/signup`, userData);
    }
};
export default signUpAPI;
