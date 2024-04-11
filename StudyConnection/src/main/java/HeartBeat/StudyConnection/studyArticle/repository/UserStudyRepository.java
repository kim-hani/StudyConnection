package HeartBeat.StudyConnection.studyArticle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import HeartBeat.StudyConnection.studyArticle.entity.UserStudy;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStudyRepository extends JpaRepository<UserStudy,String> {
}
