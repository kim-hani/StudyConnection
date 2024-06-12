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
    [types.SET_USER_INFO](state, userInfo) {
        state.userInfo = userInfo;
    },
    [types.SET_USER_RATINGS](state, userRatings) {
        state.userRatings = userRatings;
    },
    [types.ADD_RATED_USER](state, payload) {
        const { userId, studyId } = payload;
        const ratedUser = state.ratedUsers.find(
            (rated) => rated.userId === userId && rated.studyId === studyId
        );
        if (!ratedUser) {
            state.ratedUsers.push({ userId, studyId });
        }
    },
}

