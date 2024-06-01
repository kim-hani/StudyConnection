package HeartBeat.StudyConnection.dto.studyArticleDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudyApplicantsResponseDto {
    List<StudyApplicantDto> applicants;

    @Builder
    public StudyApplicantsResponseDto(List<StudyApplicantDto> applicants){
        this.applicants = applicants;
    }
}
