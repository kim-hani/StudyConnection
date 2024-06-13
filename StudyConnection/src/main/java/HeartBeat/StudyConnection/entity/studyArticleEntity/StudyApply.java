package HeartBeat.StudyConnection.entity.studyArticleEntity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "studyApplicant")
public class StudyApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studyApply_Id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "studyArticleId")
    private Long studyArticleId;

    @Builder
    public StudyApply( String userId, Long studyArticleId){

        this.userId = userId;
        this.studyArticleId = studyArticleId;
    }

    @Override
    public String toString(){
        return "<< StudyApplicant's information >>\n"
                + "study apply id: " + this.id + "\n"
                + "userId: " + this.getUserId() + "\n"
                + "study Id: " + this.studyArticleId;
    }
}
