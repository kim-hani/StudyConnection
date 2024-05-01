package HeartBeat.StudyConnection.configuration.jwt.service;

import HeartBeat.StudyConnection.configuration.jwt.TokenProvider;
import HeartBeat.StudyConnection.configuration.jwt.entity.RefreshToken;
import HeartBeat.StudyConnection.configuration.jwt.repository.RefreshTokenRepository;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected Token"));
    }

    public String createNewRefreshToken(User user){
        String newRefreshToken = tokenProvider.makeRefreshToken(user);
        String refreshTokenContent = "[" + user.getUserId() + "]" + user.getUsername();
        refreshTokenRepository.save(new RefreshToken().builder()
                .userId(user.getUserId())
                .refreshToken(refreshTokenContent + "'s refresh token")
                .build());
        return newRefreshToken;
    }
}
