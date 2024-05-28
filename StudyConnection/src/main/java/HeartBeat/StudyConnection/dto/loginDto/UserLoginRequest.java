package HeartBeat.StudyConnection.dto.loginDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginRequest {
    private String id;
    private String password;
}
