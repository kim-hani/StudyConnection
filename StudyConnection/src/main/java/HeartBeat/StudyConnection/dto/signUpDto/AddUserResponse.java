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
    private String userName;

    private String result;

    public AddUserResponse(String savesUserId, String savedUserName, String requestResult) {
        userId = savesUserId;
        userName = savedUserName;
        result = requestResult;
    }
}
