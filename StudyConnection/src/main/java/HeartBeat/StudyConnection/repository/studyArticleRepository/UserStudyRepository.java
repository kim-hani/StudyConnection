package HeartBeat.StudyConnection.repository.studyArticleRepository;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyApply;
import org.springframework.data.jpa.repository.JpaRepository;
import HeartBeat.StudyConnection.entity.studyArticleEntity.UserStudy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStudyRepository extends JpaRepository<UserStudy,String> {
    List<UserStudy> findByStudyId(Long studyId);
    List<UserStudy> findByUserId(Long userId);
}
