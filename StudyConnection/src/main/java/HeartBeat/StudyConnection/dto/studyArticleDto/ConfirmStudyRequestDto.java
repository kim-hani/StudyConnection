package HeartBeat.StudyConnection.dto.studyArticleDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConfirmStudyRequestDto {
    // 확정된 member 들의 userId
    private List<String> members;
}
