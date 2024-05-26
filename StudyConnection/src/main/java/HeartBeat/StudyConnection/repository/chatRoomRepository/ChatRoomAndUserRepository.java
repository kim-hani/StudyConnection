package HeartBeat.StudyConnection.repository.chatRoomRepository;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoomAndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomAndUserRepository extends JpaRepository<ChatRoomAndUser, Long> {
}
