package HeartBeat.StudyConnection.configuration.jwt;

import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

// JWTFactory는 테스트를 위한 Mocking 객체임
@Getter
public class JwtFactory {
    private String subject = "010-0000-0000";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = emptyMap();
}
