package HeartBeat.StudyConnection.KimJongBin;

import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import HeartBeat.StudyConnection.configuration.jwt.JwtFactory;
import HeartBeat.StudyConnection.configuration.jwt.JwtProperties;
import HeartBeat.StudyConnection.configuration.jwt.TokenProvider;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("TokenProvider의 generateToken() 테스트")
    @Test
    void generateToken(){
        // given
        User testUser = userRepository.save(User.builder()
                .userId("010-0000-0000")
                .email("test@test.com")
                .password("test1234")
                .username("testName")
                .birth("1999-12-12")
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        String userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class);

        assertThat(userId).isEqualTo(testUser.getUserId());
    }

    @DisplayName("TokenProvider의 validToked() 만료된 토큰 테스트")
    @Test
    void validToken_isInvalid(){
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("TokenProvider의 validToked() 유효한 토큰 테스트")
    @Test
    void validToken_isㅍalid(){
        // given
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("TokenProvider의 getAuthentication() 테스트 ")
    @Test
    void getAuthentication(){
        // given
        String userName = "test_user";
        String token = JwtFactory.builder()
                .claims(Map.of("username", userName))
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userName);
    }

    @DisplayName("TokenProvider의 getUserId, getUserName 테스트 ")
    @Test
    void getUserIdAndName() {
        // given
        String userId = "010-0000-0000";
        String userName = "test_user";

        String token = JwtFactory.builder()
                .claims(Map.of("userId", userId, "username", userName))
                .build()
                .createToken(jwtProperties);

        // when
        String userIdByToken = tokenProvider.getUserId(token);
        String usernameByToken = tokenProvider.getUserName(token);

        // then
        assertThat(userIdByToken).isEqualTo(userId);
        assertThat(usernameByToken).isEqualTo(userName);
    }
}
