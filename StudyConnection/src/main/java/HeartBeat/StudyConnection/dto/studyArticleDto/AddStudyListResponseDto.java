package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AddStudyListResponseDto {

    private Long id;
    private final String author;

    private final String content;

    private final int limitOfParticipants;

    private final boolean recruitment;

    private final String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AddStudyListResponseDto(StudyArticle studyArticle) {
        this.id = studyArticle.getId();
        this.author = studyArticle.getAuthor();
        this.content = studyArticle.getContent();
        this.limitOfParticipants = studyArticle.getLimitOfParticipants();
        this.recruitment = studyArticle.isRecruitment();
        this.title = studyArticle.getTitle();
        this.createdAt = studyArticle.getCreatedAt();
        this.updatedAt = studyArticle.getUpdatedAt();
    }
}
