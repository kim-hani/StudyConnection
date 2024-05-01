package HeartBeat.StudyConnection.dto.loginDto;

import lombok.*;

@NoArgsConstructor
@Setter
public class UserLoginResponse {
    private String userId;
    private String username;
    private String refreshToken;
    private String accessToken;

    @Builder
    public UserLoginResponse(String userId, String username, String refreshToken, String accessToken){
        this.userId = userId;
        this.username = username;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
