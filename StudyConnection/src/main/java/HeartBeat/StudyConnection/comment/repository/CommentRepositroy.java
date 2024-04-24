package HeartBeat.StudyConnection.comment.repository;

import HeartBeat.StudyConnection.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepositroy extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c " +
            "join fetch c.studyArticle " +
            "where c.studyArticle.id = :studyArticleId and c.parentCommentId is null " +
            "order by c.createdAt asc ")
    List<Comment> findByStudyArticleIdExceptReply(@Param("studyArticleId")
                                                  Long studyArticleId);

    @Query("select c from Comment c " +
            "join fetch c.studyArticle " +
            "where c.parentCommentId=:id order by c.createdAt asc ")
    List<Comment> findAllReplies(Long id);
}
