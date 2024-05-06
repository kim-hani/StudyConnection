import {createStore} from "vuex"
import getters from "./getters"
import mutations from "./mutation"
import actions from "./actions"

export default createStore({
    state: {
        userData: {
            userId: '010-1234-1234',
            userName: '홍길동',
            refreshToken: '',
            accessToken: '',
        },
        isAuth: false,
        errorState: ''
    },
    getters,
    mutations,
    actions
})
