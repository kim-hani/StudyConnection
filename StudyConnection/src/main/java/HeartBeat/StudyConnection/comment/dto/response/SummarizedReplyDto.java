package HeartBeat.StudyConnection.comment.dto.response;


import HeartBeat.StudyConnection.comment.CommentStatus;
import HeartBeat.StudyConnection.comment.entity.Comment;
import lombok.Getter;

@Getter
public class SummarizedReplyDto {
    private final Long id;

    private final String author;

    private final String content;

    public SummarizedReplyDto(Comment comment) {
        this.id = comment.getId();
        if(checkDeletedReply(comment)) {
            this.author = null;
            this.content = "삭제된 댓글입니다.";
        } else {
            this.author = comment.getUser().getUsername();
            this.content = comment.getContent();
        }
    }

    private boolean checkDeletedReply(Comment comment) {
        return comment.getCommentStatus() == CommentStatus.DELETED || comment.getCommentStatus() == CommentStatus.DELETED_BY_ADMIN;
    }
}
