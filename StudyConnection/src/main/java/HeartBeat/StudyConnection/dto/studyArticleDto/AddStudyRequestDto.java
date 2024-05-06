package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStudyRequestDto {

    private String title;
    private String content;
    private String authorId;

    private boolean available;
    private int limitOfParticpants;

    @Builder
    public AddStudyRequestDto(String title, String content, String author, int limitOfParticpants) {
        this.title = title;
        this.content = content;
        this.authorId = author;
        this.available = true;
        this.limitOfParticpants = limitOfParticpants;
    }

    public StudyArticle toEntity() {
        return StudyArticle.builder()
                .title(title)
                .content(content)
                .author(authorId)
                .limitOfParticipants(limitOfParticpants)
                .build();
    }
}
