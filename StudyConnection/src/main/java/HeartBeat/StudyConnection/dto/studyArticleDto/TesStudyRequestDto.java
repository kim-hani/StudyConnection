package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TesStudyRequestDto {
    private Long studyId;
    private String studyTitle;

    public TesStudyRequestDto(Study study){
        this.studyId = study.getStudyId();
        this.studyTitle = study.getStudyName();
    }
}
