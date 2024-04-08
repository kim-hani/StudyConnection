package HeartBeat.StudyConnection.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import HeartBeat.StudyConnection.user.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser,String> {

}
