import {createStore} from "vuex"
import getters from "./getters"
import mutations from "./mutation"
import actions from "./actions"

const storedUserData = JSON.parse(sessionStorage.getItem('userData')) || {
    userId: '010-0000-0000',
    userName: '홍길동',
    refreshToken: '',
    accessToken: ''
};

const isAuth = storedUserData.accessToken !== '';
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
