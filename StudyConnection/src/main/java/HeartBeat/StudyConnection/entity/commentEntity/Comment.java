package HeartBeat.StudyConnection.entity.commentEntity;


import HeartBeat.StudyConnection.CommentStatus;
import HeartBeat.StudyConnection.entity.BaseTimeEntity;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studyArticle_id")
    private StudyArticle studyArticle;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Column(updatable = false)
    private Long parentCommentId;


    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL,fetch = LAZY)
    private List<Comment> childComments = new ArrayList<>();

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    @Builder
    private Comment(@NotNull StudyArticle studyArticle,
                    @NotNull User user,
                    String content,
                    CommentStatus commentStatus,
                    Long parentCommentId) {
        this.studyArticle = studyArticle;
        this.user = user;
        this.content = content;
        this.commentStatus = commentStatus;
        this.parentCommentId = parentCommentId;
    }

    public void addChildComment(Comment child) {
        child.setParentCommentId(this.getId());
        this.getChildComments().add(child);
    }

    public void changeStudyArticle(StudyArticle studyArticle) {
        if (this.studyArticle != null) {
            this.studyArticle.getComments().remove(this);
        }

        this.studyArticle = studyArticle;
        this.studyArticle.getComments().add(this);
    }


    public void updateContent(String content) {
        this.content = content;
        this.commentStatus = CommentStatus.EDITED;
    }

    public void markedAsDeleted(boolean isAdmin) {
        if(isAdmin) {
            this.commentStatus = CommentStatus.DELETED_BY_ADMIN;
        } else {
            this.commentStatus = CommentStatus.DELETED;
        }
    }

}
