package HeartBeat.StudyConnection.dto.studyArticleDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyApplyRequestDto {
    private String userId;

    public StudyApplyRequestDto(String userId){
        this.userId = userId;
    }
}
