package HeartBeat.StudyConnection.repository.studyArticleRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyArticleRepository extends JpaRepository<StudyArticle,Long> {
    @Query("SELECT p FROM StudyArticle p ORDER BY p.uploadDate DESC")
    List<StudyArticle> findAllDesc();
}
