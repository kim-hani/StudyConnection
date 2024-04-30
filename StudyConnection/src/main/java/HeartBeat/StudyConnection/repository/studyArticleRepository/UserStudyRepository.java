package HeartBeat.StudyConnection.repository.studyArticleRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import HeartBeat.StudyConnection.entity.studyArticleEntity.UserStudy;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStudyRepository extends JpaRepository<UserStudy,String> {
}
