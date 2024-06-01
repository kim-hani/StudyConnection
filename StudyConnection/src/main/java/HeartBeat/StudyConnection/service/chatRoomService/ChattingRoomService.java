package HeartBeat.StudyConnection.service.chatRoomService;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoom;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.chatRoomRepository.ChatRoomRepository;
import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public void createChatRoom(String roomName, List<User> member, Long studyId){
        // 새로운 채팅방 생성
        for(User participant : member){
            chatRoomRepository.save(ChatRoom.builder()
                    .roomName(roomName)
                    .studyId(studyId)
                    .userId(participant.getUserId())
                    .build());
        }
    }
    
    // Study의 Id로 채팅방 찾기
    public List<ChatRoom> findChatRoomsByStudyId(Long studyId){
        return chatRoomRepository.findByStudyId(studyId);
    }

    // User의 Id로 채팅방 찾기
    public List<ChatRoom> findChatRoomsByUserId(String userId) {
        return chatRoomRepository.findByUserId(userId);
    }
}
