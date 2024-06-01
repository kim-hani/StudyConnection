package HeartBeat.StudyConnection.dto.studyArticleDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyApplicantDto {
    private String userId;
    private String username;
    private String birth;
    private String email;
    private Long studyId;

    @Builder
    public StudyApplicantDto(String userId, String username, String birth, String email, Long studyId){
        this.userId = userId;
        this.username = username;
        this.birth = birth;
        this.email = email;
        this.studyId = studyId;
    }

}
