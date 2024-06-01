package HeartBeat.StudyConnection.repository.commentRepository;

import HeartBeat.StudyConnection.entity.commentEntity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c " +
            "join fetch c.studyArticle " +
            "where c.studyArticle.id = :studyArticleId and c.parentCommentId is null " +
            "order by c.createdAt asc ")
    List<Comment> findByStudyArticleIdExceptReply(@Param("studyArticleId")
                                                  Long studyArticleId);

    @Query("select c from Comment c " +
            "join fetch c.studyArticle " +
            "where c.parentCommentId= :id order by c.createdAt asc ")
    List<Comment> findAllReplies(@Param("id") Long id);
}
