package HeartBeat.StudyConnection.repository.userInfoRepository;

import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);

    @Override
    public User save(User entity);
}
