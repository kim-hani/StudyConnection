package HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.service;

import HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.entity.RefreshToken;
import HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.refreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected Token"));
    }

    public RefreshToken save(String userId, String refreshToken){
        return refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
    }
}
