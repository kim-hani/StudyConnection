package HeartBeat.StudyConnection.chatRoomMake.repository;

import HeartBeat.StudyConnection.chatRoomMake.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
