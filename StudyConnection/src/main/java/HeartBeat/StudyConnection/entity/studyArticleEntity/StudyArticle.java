package HeartBeat.StudyConnection.entity.studyArticleEntity;


import HeartBeat.StudyConnection.entity.BaseTimeEntity;
import HeartBeat.StudyConnection.entity.commentEntity.Comment;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STUDYARTICLE")
@EntityListeners(AuditingEntityListener.class)
public class StudyArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studyArticle_Id")
    private Long id;        // 게시글 아이디

    @Column(name = "author_id")
    private String authorId;   // 작성자의 아이디

    @Column(name = "author_name")
    private String authorName;   // 작성자의 아이디

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(name = "limit_of_participants")
    private int limitOfParticipants;    // 스터디 정원

    @Column(name = "recruitment")
    private Boolean recruitment = true;      // 참여 가능 여부

    @Column(columnDefinition = "TEXT",nullable = false)
    private String title;       // 스터디 이름

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "studyArticle", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public StudyArticle(Long id, String authorId, String content, int limitOfParticipants, Boolean recruitment,
                        String title, String comment, String authorName){
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.limitOfParticipants = limitOfParticipants;
        this.recruitment = recruitment;
        this.title = title;
    }


    public void update(String title,String content){
        this.title = title;
        this.content = content;
    }

    // 작성자 확인 메서드
    public boolean isAuthor(User user){
        return this.authorId.equals(user.getUserId());
    }
}