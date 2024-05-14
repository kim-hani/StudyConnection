package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    private final int limitOfParticipants;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public StudyResponseDto(StudyArticle entity) {
        this.id      = entity.getId();
        this.title   = entity.getTitle();
        this.content = entity.getContent();
        this.author  = entity.getAuthor();
        this.limitOfParticipants = entity.getLimitOfParticipants();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
