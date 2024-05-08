package HeartBeat.StudyConnection.configuration.jwt.service;

import HeartBeat.StudyConnection.configuration.jwt.entity.RefreshToken;
import HeartBeat.StudyConnection.configuration.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> findByRefreshToken(String refreshToken){
        return refreshTokenRepository.refreshToken(refreshToken);
    }

    public RefreshToken save(String userId, String refreshToken){
        return refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
    }

    public Optional<RefreshToken> findByUserId(String userId){
        return refreshTokenRepository.findByUserId(userId);
    }
}
