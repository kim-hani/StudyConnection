package HeartBeat.StudyConnection.dto.studyArticleDto;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;

public class StudyResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public StudyResponseDto(StudyArticle entity) {
        this.id      = entity.getId();
        this.title   = entity.getTitle();
        this.content = entity.getContent();
        this.author  = entity.getAuthor();
    }
}
