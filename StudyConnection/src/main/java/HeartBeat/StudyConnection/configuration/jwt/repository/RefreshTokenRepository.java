package HeartBeat.StudyConnection.configuration.jwt.repository;

import HeartBeat.StudyConnection.configuration.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUserId(String userId);

    Optional<RefreshToken> refreshToken(String refreshTokenValue);

}
