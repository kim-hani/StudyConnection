package HeartBeat.StudyConnection.entity.rating;

import HeartBeat.StudyConnection.dto.ratingDto.UserRatingDto;
import HeartBeat.StudyConnection.entity.BaseTimeEntity;
import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRating extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_rating_id")
    private Long id;

    @Setter
    private Float score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Setter
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Builder
    public UserRating(User user,
                      Study study,
                      Float score,
                      String content,
                      User author) {
        this.user = user;
        this.study = study;
        this.score = score;
        this.content = content;
        this.author = author;
    }

}

