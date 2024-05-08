package HeartBeat.StudyConnection.dto.signUpDto;

import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddUserRequest {
    private String userId;
    private String password;
    private String email;
    private String birth;
    private String username;

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .email(email)
                .birth(birth)
                .build();
    }
}
