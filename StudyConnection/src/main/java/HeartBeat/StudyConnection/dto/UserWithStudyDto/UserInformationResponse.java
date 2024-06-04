package HeartBeat.StudyConnection.dto.UserWithStudyDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInformationResponse {
    private String username;
    private String userId;
    private String age;
    private String email;
    // 평가 기능 완료되면 별점 등 추가

    // 참여중인 스터디 목록
    private List<UserStudiesResponse> studyList;

    @Builder
    public UserInformationResponse(String username, String userId, String age, String email, List<UserStudiesResponse> studyList){
        this.username = username;
        this.userId = userId;
        this.age = age;
        this.email = email;
        this.studyList = studyList;
    }
}
