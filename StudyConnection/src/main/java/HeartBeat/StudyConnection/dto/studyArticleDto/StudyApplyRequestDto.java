package HeartBeat.StudyConnection.dto.studyArticleDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyApplyRequestDto {
    private String userId;

    public StudyApplyRequestDto(String userId){
        this.userId = userId;
    }
}
