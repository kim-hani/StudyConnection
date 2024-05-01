package HeartBeat.StudyConnection.dto.loginDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest {
    private String id;
    private String password;
}
