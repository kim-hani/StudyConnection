package HeartBeat.StudyConnection.userInfo.repository;

import HeartBeat.StudyConnection.userInfo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
