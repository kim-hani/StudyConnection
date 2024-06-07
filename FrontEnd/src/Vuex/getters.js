export default {
    getIsAuth: state => state.isAuth,
    getUserId: state => state.userData.userId,
    getAcesstoken: state => state.userData.accessToken,
};
