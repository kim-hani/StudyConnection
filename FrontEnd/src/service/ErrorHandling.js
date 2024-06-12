export const handleApiError = (error) => {
    if (error.message.indexOf('Network Error') > -1) {
        alert('서버에 접속할 수 없습니다. 상태를 확인해주세요.');
    } else if (error.response) {
        const status = error.response.status;
        switch (status) {
            case 400:
                console.log('잘못된 요청입니다.');
                break;
            case 401:
                console.log('인증 정보가 유효하지 않습니다.');
                break;
            case 403:
                console.log('접근 권한이 없습니다.');
                break;
            case 404:
                console.log('요청한 자원을 찾을 수 없습니다.');
                break;
            case 409:
                console.log('이미 존재하는 자원입니다.');
                break;
            case 500:
                console.log('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                break;
            default:
                alert('알 수 없는 오류가 발생했습니다.');
                break;
        }
    } else {
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    }
};
