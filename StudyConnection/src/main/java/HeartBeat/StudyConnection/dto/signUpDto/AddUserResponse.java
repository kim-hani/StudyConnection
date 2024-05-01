package HeartBeat.StudyConnection.dto.signUpDto;

import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserResponse {
    private String userId;
    private String username;

    public AddUserResponse(String savesUserId, String savedUserName) {
        userId = savesUserId;
        username = savedUserName;
    }
}
