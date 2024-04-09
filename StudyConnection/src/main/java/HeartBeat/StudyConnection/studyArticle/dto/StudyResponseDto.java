package HeartBeat.StudyConnection.studyArticle.dto;

import HeartBeat.StudyConnection.studyArticle.entity.StudyArticle;

public class StudyResponseDto {
    private int id;
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
