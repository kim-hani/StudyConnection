package HeartBeat.StudyConnection.studyArticle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStudyRequestDto {
    private String title;
    private String content;

    @Builder
    public UpdateStudyRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
