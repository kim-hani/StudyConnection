package HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequest {
    // client에서 server로 refresh token을 보내 새로운 access token 요청
    private String refreshToken;
}
