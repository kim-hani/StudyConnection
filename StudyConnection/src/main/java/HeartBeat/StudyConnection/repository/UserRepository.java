package HeartBeat.StudyConnection.repository;

import HeartBeat.StudyConnection.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
