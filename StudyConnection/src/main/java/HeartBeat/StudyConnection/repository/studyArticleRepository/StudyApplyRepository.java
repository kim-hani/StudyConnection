package HeartBeat.StudyConnection.repository.studyArticleRepository;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyApplyRepository extends JpaRepository<StudyApply, Long> {
    List<StudyApply> findByStudyArticleId(Long studyArticleId);
}
