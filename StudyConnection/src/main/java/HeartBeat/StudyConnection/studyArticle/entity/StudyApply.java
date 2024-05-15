package HeartBeat.StudyConnection.studyArticle.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "studyApplicant")
public class StudyApply {
    @Id
    private String id;
}
