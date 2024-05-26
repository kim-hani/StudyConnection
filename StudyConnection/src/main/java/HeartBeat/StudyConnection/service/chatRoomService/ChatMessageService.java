package HeartBeat.StudyConnection.service.chatRoomService;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatMessage;
import HeartBeat.StudyConnection.repository.chatRoomRepository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    final ChatMessageRepository chatMessageRepository;

    // 메세지 저장
    public void saveAllChatMessage(List<ChatMessage> chats){
        chatMessageRepository.saveAll(chats);
    }

    // 메세지 내용 불러오기 (studyId + dateTime 오래된 순)
    public List<ChatMessage> loadChatMessagesByStudyId(Long studyId){
        return chatMessageRepository.findByStudyIdOrderByTimestampAsc(studyId);
    }

}
