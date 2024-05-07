package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AddStudyListResponseDto {

    private final String author;

    private final String content;

    private final int limitOfParticipants;

    private final boolean available;

    private final String title;


    public AddStudyListResponseDto(StudyArticle studyArticle) {
        this.author = studyArticle.getAuthor();
        this.content = studyArticle.getContent();
        this.limitOfParticipants = studyArticle.getLimitOfParticipants();
        this.available = studyArticle.isAvailable();
        this.title = studyArticle.getTitle();
    }
}
