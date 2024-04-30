package HeartBeat.StudyConnection.controller.dto.userInfoDto;

import lombok.Getter;

@Getter
public class AddUserRequest {
    private String userId;
    private String password;
    private String email;
    private String birth;
    private String username;
}
