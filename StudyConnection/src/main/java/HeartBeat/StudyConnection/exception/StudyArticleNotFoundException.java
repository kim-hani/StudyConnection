package HeartBeat.StudyConnection.exception;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;

public class StudyArticleNotFoundException extends LocalizedMessageException {

    public StudyArticleNotFoundException() {
        super(HttpStatus.NOT_FOUND, "notfound.post");
    }
}
