package HeartBeat.StudyConnection.dto.loginDto;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class UserLoginResponse {
    private String userId;
    private String userName;
    private String refreshToken;
    private String accessToken;

    @Builder
    public UserLoginResponse(String userId, String username, String refreshToken, String accessToken){
        this.userId = userId;
        this.userName = username;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
