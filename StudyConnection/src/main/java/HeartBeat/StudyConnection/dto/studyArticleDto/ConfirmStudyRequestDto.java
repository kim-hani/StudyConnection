package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmStudyRequestDto {
    // 확정된 member 들의 userId
    private List<String> members;
    private String studyTitle;

    @Builder
    public ConfirmStudyRequestDto(List<String> members, String studyTitle){
        this.members = members;
        this.studyTitle = studyTitle;
    }
}
