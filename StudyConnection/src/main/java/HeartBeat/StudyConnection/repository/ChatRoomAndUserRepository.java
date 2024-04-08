package HeartBeat.StudyConnection.repository;

import HeartBeat.StudyConnection.entity.ChatRoomAndUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomAndUserRepository extends JpaRepository<ChatRoomAndUser, Long> {
}
