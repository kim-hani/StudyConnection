package HeartBeat.StudyConnection.role;

import HeartBeat.StudyConnection.configuration.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.*;

@Target({ElementType.METHOD})
@Retention(RUNTIME)
@Documented
@SecurityRequirement(name = TokenProvider.AUTHORIZATION)
@Secured(UserAuthNames.ROLE_USER)
public @interface UserAuth {
}
