export default {
    getUserId: state => state.userId,
    getErrorstate: state => state.errorstate,
    getIsAuth: state => state.isAuth,
    loggedIn(state) {
        return !!state.user
    }
}
