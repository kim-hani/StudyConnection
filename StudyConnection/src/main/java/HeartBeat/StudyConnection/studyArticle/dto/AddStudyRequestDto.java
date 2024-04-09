package HeartBeat.StudyConnection.studyArticle.dto;

import HeartBeat.StudyConnection.studyArticle.entity.StudyArticle;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
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

    @Builder
    public AddStudyRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public StudyArticle toEntity() {
        return StudyArticle.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }


}