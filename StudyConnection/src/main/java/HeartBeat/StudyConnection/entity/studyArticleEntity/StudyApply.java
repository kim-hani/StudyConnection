package HeartBeat.StudyConnection.entity.studyArticleEntity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "studyApplicant")
public class StudyApply {
    @Id
    private String userId;

    @Column(name = "username")
    private String username;

    @Column(name = "studyArticleId")
    private Long studyArticleId;

    @Builder
    public StudyApply(String userId, String username, Long studyArticleId){
        this.userId = userId;
        this.username = username;
        this.studyArticleId = studyArticleId;
    }
}
