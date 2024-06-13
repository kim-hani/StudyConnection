import * as types from './mutation_types'

export default {
    [types.SET_USER](state, user) {
        state.userData = user;
    },
    [types.SET_IS_AUTH](state, isAuth) {
        state.isAuth = isAuth;
    },
    [types.SET_ERROR_STATE](state, errorState) {
        state.errorState = errorState;
    },
}

