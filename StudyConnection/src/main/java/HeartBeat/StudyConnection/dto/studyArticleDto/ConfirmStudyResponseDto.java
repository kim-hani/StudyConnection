package HeartBeat.StudyConnection.dto.studyArticleDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmStudyResponseDto {
    private String studyName;
    private String chattingRoomName;
    private List<String> membersId;

    @Builder
    public ConfirmStudyResponseDto(String studyName, String chattingRoomName, List<String> membersId){
        this.studyName = studyName;
        this.chattingRoomName = chattingRoomName;
        this.membersId = membersId;
    }
}
