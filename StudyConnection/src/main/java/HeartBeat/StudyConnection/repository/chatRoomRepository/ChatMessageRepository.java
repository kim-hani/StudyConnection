package HeartBeat.StudyConnection.repository.chatRoomRepository;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByStudyIdOrderByDateTimeAsc(Long studyId);
}
