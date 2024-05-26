package HeartBeat.StudyConnection.repository.chatRoomRepository;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByStudyId(Long studyId);

    @Query("SELECT cr FROM ChatRoom cr JOIN cr.chatRoomAndUsers cru JOIN cru.user u WHERE u.userId = :userId")
    List<ChatRoom> findChatRoomsByUserId(@Param("userId") String userId);

}
