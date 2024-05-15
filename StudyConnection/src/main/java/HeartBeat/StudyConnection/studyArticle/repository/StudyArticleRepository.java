package HeartBeat.StudyConnection.studyArticle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import HeartBeat.StudyConnection.studyArticle.entity.StudyArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyArticleRepository extends JpaRepository<StudyArticle,Integer> {
    @Query("SELECT p FROM Posts p ORDER BY p.uploadDate DESC")
    List<StudyArticle> findAllDesc();
}
