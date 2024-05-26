package HeartBeat.StudyConnection.service.chatRoomService;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoom;
import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoomAndUser;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.chatRoomRepository.ChatRoomAndUserRepository;
import HeartBeat.StudyConnection.repository.chatRoomRepository.ChatRoomRepository;
import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomAndUserRepository chatRoomAndUserRepository;
    private final UserRepository userRepository;

    public ChatRoom createChatRoom(String roomName, List<User> member, Long studyId){
        // 새로운 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);
        chatRoom.setStudyId(studyId);


        // 사용자 엔티티들을 찾아서 채팅방에 추가
        Set<User> users = new HashSet<>(member);

        // 채팅방 저장
        chatRoomRepository.save(chatRoom);
        addParticipantsToChatRoom(chatRoom, users);
        return chatRoom;
    }

    public void addParticipantsToChatRoom(ChatRoom chatRoom, Set<User> users){
        for(User user : users){
            ChatRoomAndUser chatRoomAndUser = new ChatRoomAndUser();
            chatRoomAndUser.setChatRoom(chatRoom);
            chatRoomAndUser.setUser(user);
            chatRoomAndUserRepository.save(chatRoomAndUser);
        }
    }

    public List<ChatRoom> findByStudyId(Long studyId){
        return chatRoomRepository.findByStudyId(studyId);
    }

    public List<ChatRoom> findChatRoomsByUserId(String userId) {
        return chatRoomRepository.findChatRoomsByUserId(userId);
    }
}
