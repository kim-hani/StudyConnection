package HeartBeat.StudyConnection.studyArticle.dto;

import HeartBeat.StudyConnection.studyArticle.entity.StudyArticle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStudyRequestDto {

    private String title;
    private String content;
    private String authorId;

    @Builder
    public AddStudyRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.authorId = author;
    }

    public StudyArticle toEntity() {
        return StudyArticle.builder()
                .title(title)
                .content(content)
                .author(authorId)
                .build();
    }


}
