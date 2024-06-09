package HeartBeat.StudyConnection.oneTwo;

import HeartBeat.StudyConnection.configuration.jwt.JwtAuthentication;
import HeartBeat.StudyConnection.role.UserRole;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuth {
    public static void withUser(String userId) {
        SecurityContextHolder.getContext()
                .setAuthentication(new JwtAuthentication(userId, UserRole.USER));
    }



    public static void withAdmin(String userId) {
        SecurityContextHolder.getContext()
                .setAuthentication(new JwtAuthentication(userId, UserRole.ADMIN));
    }
}
