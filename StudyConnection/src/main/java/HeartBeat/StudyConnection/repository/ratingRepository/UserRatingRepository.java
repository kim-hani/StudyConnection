package HeartBeat.StudyConnection.repository.ratingRepository;

import HeartBeat.StudyConnection.entity.rating.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
    List<UserRating> findAll();

    List<UserRating> findByUser_userId(String userId);
}
