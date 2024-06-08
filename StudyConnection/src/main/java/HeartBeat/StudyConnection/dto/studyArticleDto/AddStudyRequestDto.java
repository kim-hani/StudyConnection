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
    private int limitOfParticipants;

    @Builder
    public AddStudyRequestDto(String title, String content, String author, int limitOfParticipants) {
        this.title = title;
        this.content = content;
        this.authorId = author;
        this.limitOfParticipants = limitOfParticipants;
    }

    public StudyArticle toEntity() {
        return StudyArticle.builder()
                .title(title)
                .content(content)
                .author(authorId)
                .recruitment(true)
                .limitOfParticipants(limitOfParticipants)
                .build();
    }
}
