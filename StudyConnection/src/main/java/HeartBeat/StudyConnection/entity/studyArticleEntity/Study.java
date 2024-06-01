package HeartBeat.StudyConnection.entity.studyArticleEntity;

import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Study {
    @Id
    private Long studyId;

    @Column(name = "study_name")
    private String studyName;

    @OneToMany(mappedBy = "study")
    private Set<UserStudy> userStudies = new HashSet<>();


}
