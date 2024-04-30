package HeartBeat.StudyConnection.repository.userInfoRepository;

import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
