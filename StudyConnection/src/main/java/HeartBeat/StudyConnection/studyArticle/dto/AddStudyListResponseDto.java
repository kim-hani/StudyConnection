package HeartBeat.StudyConnection.studyArticle.dto;

import HeartBeat.StudyConnection.studyArticle.entity.StudyArticle;
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

    private final boolean online;

    private final LocalDateTime uploadDate;

    public AddStudyListResponseDto(StudyArticle studyArticle) {
        this.author = studyArticle.getAuthor();
        this.content = studyArticle.getContent();
        this.limitOfParticipants = studyArticle.getLimitOfParticipants();
        this.available = studyArticle.isAvailable();
        this.title = studyArticle.getTitle();
        this.online = studyArticle.isOnline();
        this.uploadDate = studyArticle.getUploadDate();
    }
}
