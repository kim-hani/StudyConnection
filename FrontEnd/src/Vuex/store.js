import {createStore} from "vuex"
import getters from "./getters"
import mutations from "./mutation"
import actions from "./actions"

const storedUserData = JSON.parse(localStorage.getItem('userData')) || {
    userId: '010-0000-0000',
    username: '홍길동',
    refreshToken: '',
    accessToken: ''
};

const isAuth = localStorage.getItem('accessToken') !== null;
export default createStore({
    state: {
        userData: storedUserData,
        isAuth: isAuth, // accessToken이 있으면 인증된 것으로 간주
        errorState: ''
    },
    getters,
    mutations,
    actions
})
