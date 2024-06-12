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
    private String authorName;

    private Boolean recruitment;
    private int limitOfParticipants;

    @Builder
    public AddStudyRequestDto(String title, String content, String authorId, int limitOfParticipants, String authorName) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.limitOfParticipants = limitOfParticipants;
        this.authorName = authorName;
    }

    public StudyArticle toEntity() {
        return StudyArticle.builder()
                .title(title)
                .content(content)
                .authorId(authorId)
                .authorName(authorName)
                .recruitment(true)
                .limitOfParticipants(limitOfParticipants)
                .build();
    }
}
