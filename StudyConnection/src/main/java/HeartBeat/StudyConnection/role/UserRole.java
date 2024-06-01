package HeartBeat.StudyConnection.role;

import HeartBeat.StudyConnection.configuration.jwt.AppAuthentication;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static HeartBeat.StudyConnection.role.UserAuthNames.*;

@Getter
public enum UserRole {
    USER(ROLE_USER),
    ADMIN(combine(ROLE_USER, ROLE_ADMIN));

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    private static final Map<String, UserRole> BY_LABEL =
            Stream.of(values()).collect(Collectors.toMap(UserRole::getName, e -> e));

    public static UserRole of(String name) {
        return BY_LABEL.get(name);
    }

    public static UserRole from(AppAuthentication auth) {
        return auth.getUserRole();
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}