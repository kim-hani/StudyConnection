package HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.repository;

import HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUserId(String userId);

    Optional<RefreshToken> refreshToken(String refreshTokenValue);

}
