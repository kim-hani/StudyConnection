package HeartBeat.StudyConnection.repository.chatRoomMakeRepository;

import HeartBeat.StudyConnection.entity.chatRoomMakeEntity.ChatRoomAndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomAndUserRepository extends JpaRepository<ChatRoomAndUser, Long> {
}
