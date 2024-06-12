package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;

   private String authorId;

    private final Boolean recruitment; // 모집중인지 여부

    private final Boolean available; // 스터디 완전 끝났는지 여부

    private final int limitOfParticipants;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public StudyResponseDto(StudyArticle entity, Boolean available) {
        this.id      = entity.getId();
        this.title   = entity.getTitle();
        this.content = entity.getContent();
        this.author  = entity.getAuthor();
        this.authorId = entity.getWriter().getUserId();
        this.limitOfParticipants = entity.getLimitOfParticipants();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.recruitment = entity.getRecruitment();
        this.available = available;
    }
}
