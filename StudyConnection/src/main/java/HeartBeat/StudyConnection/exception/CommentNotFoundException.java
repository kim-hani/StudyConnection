package HeartBeat.StudyConnection.exception;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends  LocalizedMessageException{

    public CommentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "notfound.comment");
    }
}
