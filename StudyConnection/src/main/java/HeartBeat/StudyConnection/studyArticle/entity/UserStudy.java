package HeartBeat.StudyConnection.studyArticle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "userStudy")
public class UserStudy {
    // 유저 테이블이랑 연관관계 매핑 해야됨
    @Id
    private Long id;

   /* @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; */

    @ManyToOne
    @JoinColumn(name = "study_id")
    private StudyArticle study;

}
