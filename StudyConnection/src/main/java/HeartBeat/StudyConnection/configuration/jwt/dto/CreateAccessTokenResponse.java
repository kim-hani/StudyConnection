package HeartBeat.StudyConnection.configuration.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAccessTokenResponse {
    // 요청에 대한 결과로 access token을 client로 응답
    private String accessToken;
}
