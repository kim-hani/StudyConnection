package HeartBeat.StudyConnection.configuration.jwt.service;

import HeartBeat.StudyConnection.configuration.jwt.TokenProvider;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        // 토큰 유효성 X -> 예외
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token: expired refresh token");
        }

        String userId = refreshTokenService.findByRefreshToken(refreshToken).get().getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(3));
    }
}
