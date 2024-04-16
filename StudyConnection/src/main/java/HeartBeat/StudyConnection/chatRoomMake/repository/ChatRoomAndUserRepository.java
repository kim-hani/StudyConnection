package HeartBeat.StudyConnection.chatRoomMake.repository;

import HeartBeat.StudyConnection.chatRoomMake.entity.ChatRoomAndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomAndUserRepository extends JpaRepository<ChatRoomAndUser, Long> {
}
