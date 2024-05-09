import {SET_USER, SET_IS_AUTH, SET_ERROR_STATE} from './mutation_types'
import loginAPI from '../service/LoginAPI'
import signUpAPI from '../service/SignUpAPI'

export default {
    async login({ commit }, LoginData) {
        try {
            const loginResponse = await loginAPI.doLogin(LoginData);
            console.log('Response:', loginResponse);
            console.log('Status:', typeof loginResponse.status, loginResponse.status);
            // 로그인 응답의 상태 코드에 따라 처리
            if (loginResponse.status === 200) {
                console.log('login success')
                commit(SET_IS_AUTH, true);
                commit(SET_USER, loginResponse.data);
                localStorage.setItem('userData', JSON.stringify(loginResponse.data));
                localStorage.setItem('userId', loginResponse.data.userId);
                localStorage.setItem('username', loginResponse.data.username);
                localStorage.setItem('accessToken', loginResponse.data.accessToken);
                localStorage.setItem('refreshToken', loginResponse.data.refreshToken);
                return true;
            } else {
                console.log('Inside else block: Login failed with status', loginResponse.status);
                commit(SET_ERROR_STATE, loginResponse.data.errorState || 'Login failed');
                return false;
            }
        } catch (error) {
            // 네트워크 에러 또는 예외 처리
            commit('SET_ERROR_STATE', error.message || 'Network error');
            return false;
        }
    },

    async signUp({ commit }, userData) {
        try {
            const response = await signUpAPI.doSignUp(userData);
                console.log(response);
                return true;
        } catch (error) {
            console.log(error.response.status);
            commit(SET_ERROR_STATE, error.response.status);
            return false;
        }
    },

    async logout({ commit }) {
        localStorage.removeItem('userId');
        localStorage.removeItem('username');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        commit(SET_IS_AUTH, false);
        console.log(await loginAPI.doLogout());
    }
}
