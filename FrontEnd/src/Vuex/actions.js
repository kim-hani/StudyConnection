import axios from 'axios'
import {SET_USER, SET_IS_AUTH, SET_ERROR_STATE, ADD_RATED_USER} from './mutation_types'
import loginAPI from '../service/LoginAPI'
import signUpAPI from '../service/SignUpAPI'

const serverUrl = '//13.125.49.195:8080';
const accessToken = localStorage.getItem('accessToken');
export default {

    async login({ commit }, LoginData) {
        try {
            const loginResponse = await loginAPI.doLogin(LoginData);
            console.log('Response:', loginResponse);
            console.log('Status:', typeof loginResponse.status, loginResponse.status);

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
    },

    async getUserInfo({ commit }, userId) {
        try {
            const response = await axios.get(serverUrl + `/api/userinfo/${userId}`);
            commit('SET_USER_INFO', response.data);
        } catch (error) {
            commit('SET_ERROR_STATE', error.message || 'Failed to fetch user info');
        }
    },

    async fetchUserRatings({ commit }, userId) {
        try {
            const response = await axios.get(serverUrl + `/api/user-ratings/${userId}`);
            console.log('Response:', response.data);
            commit('SET_USER_RATINGS', response.data);
        } catch (error) {
            console.error('평가 데이터를 불러오는 중 오류 발생:', error);
        }
    },

    async rateMember({ commit }, ratingData) {
        try {
            const response = await axios.post(
                serverUrl + `/api/user-ratings`, ratingData,
                {
                    headers: {
                        'Authorization': `Bearer ${accessToken}`, // Authorization 헤더에 토큰 추가
                    },
                }
            );
            commit(ADD_RATED_USER, { userId: ratingData.userId, studyId: ratingData.studyId }); // 평가 완료된 사용자 추가
            console.log(response);
        } catch (error) {
            console.error('평가 저장 에러 발생:', error);
        }
    },
}
