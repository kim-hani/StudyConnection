package HeartBeat.StudyConnection.repository.studyArticleRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyArticleRepository extends JpaRepository<StudyArticle,Long> {

    List<StudyArticle> findAllByOrderByCreatedAtDesc();
}
