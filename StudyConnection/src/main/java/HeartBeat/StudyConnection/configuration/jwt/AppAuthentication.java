package HeartBeat.StudyConnection.configuration.jwt;

import HeartBeat.StudyConnection.role.UserRole;
import org.springframework.security.core.Authentication;

// 사용자 인증 및 권한 정보
public interface AppAuthentication extends Authentication {
    String getUserId();
    UserRole getUserRole();
    boolean isAdmin();

}
