package HeartBeat.StudyConnection.dto.UserWithStudyDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserStudiesResponse {
    private String studyName;
    private Long studyId;
    private List<UserNameIdResponse> participants;

    @Builder
    public UserStudiesResponse(String studyName, Long studyId, List<UserNameIdResponse> participants){
        this.studyId = studyId;
        this.studyName = studyName;
        this.participants = participants;
    }
}
