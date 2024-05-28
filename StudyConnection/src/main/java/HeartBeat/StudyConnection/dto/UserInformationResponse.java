package HeartBeat.StudyConnection.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInformationResponse {
    private String username;
    private String userId;
    private String age;
    private String email;
    // 평가 기능 완료되면 별점 등 추가

    @Builder
    public UserInformationResponse(String username, String userId, String age, String email){
        this.username = username;
        this.userId = userId;
        this.age = age;
        this.email = email;
    }
}
