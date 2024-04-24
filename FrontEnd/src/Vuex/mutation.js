import * as types from './mutation_types'

export default {
    [types.USER_ID](state, user) {
        state.user = user
    },
    [types.ERROR_STATE] (state, errorstate) {
        state.errorstate = errorstate
    },
    [types.IS_AUTH](state, isAuth) {
        state.isAuth = isAuth
    },
}
