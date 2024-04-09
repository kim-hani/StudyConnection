package HeartBeat.StudyConnection.studyArticle.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStudyRequestDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
    private String author;


}
