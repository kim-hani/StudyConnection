package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    public StudyResponseDto(StudyArticle entity) {
        this.id      = entity.getId();
        this.title   = entity.getTitle();
        this.content = entity.getContent();
        this.author  = entity.getAuthor();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
