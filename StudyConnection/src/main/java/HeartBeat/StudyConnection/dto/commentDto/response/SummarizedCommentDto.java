package HeartBeat.StudyConnection.dto.commentDto.response;

import HeartBeat.StudyConnection.CommentStatus;
import HeartBeat.StudyConnection.entity.commentEntity.Comment;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class SummarizedCommentDto {
    private final Long id;

    private final String author;

    private final String content;

    private final List<SummarizedReplyDto> replies;

    public SummarizedCommentDto(Comment comment, List<SummarizedReplyDto> replies) {
        this.id = comment.getId();
        if(checkDeletedComment(comment)) {
            this.author = null;
            this.content = "삭제된 댓글입니다.";
        } else {
            this.author = comment.getUser().getUsername();
            this.content = comment.getContent();
        }
        this.replies = Objects.requireNonNullElseGet(replies, List::of);
    }

    private boolean checkDeletedComment(Comment comment) {
        return comment.getCommentStatus() == CommentStatus.DELETED || comment.getCommentStatus() == CommentStatus.DELETED_BY_ADMIN;
    }
}
