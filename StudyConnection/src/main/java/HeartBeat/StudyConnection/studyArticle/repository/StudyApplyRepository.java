package HeartBeat.StudyConnection.studyArticle.repository;

import HeartBeat.StudyConnection.studyArticle.entity.StudyApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyApplyRepository extends JpaRepository<StudyApply,String> {
}
