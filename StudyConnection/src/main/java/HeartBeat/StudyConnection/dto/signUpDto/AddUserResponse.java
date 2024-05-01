package HeartBeat.StudyConnection.dto.signUpDto;

import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@Setter
public class AddUserResponse {
    private String userId;
    private String username;

    public AddUserResponse(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
    }


}
