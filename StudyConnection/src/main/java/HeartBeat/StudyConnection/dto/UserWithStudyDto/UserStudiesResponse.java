package HeartBeat.StudyConnection.dto.UserWithStudyDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserStudiesResponse {
    private String studyName;
    private Long studyId;
    private boolean available;
    private List<UserNameIdResponse> participants;

    @Builder
    public UserStudiesResponse(String studyName, Long studyId, List<UserNameIdResponse> participants, boolean available){
        this.studyId = studyId;
        this.studyName = studyName;
        this.participants = participants;
        this.available = available;
    }

    public boolean getAvailable() {
        return this.available;
    }
}
