package HeartBeat.StudyConnection.service.chatRoomMakeService;

import HeartBeat.StudyConnection.entity.chatRoomMakeEntity.ChatRoom;
import HeartBeat.StudyConnection.entity.chatRoomMakeEntity.ChatRoomAndUser;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.chatRoomMakeRepository.ChatRoomAndUserRepository;
import HeartBeat.StudyConnection.repository.chatRoomMakeRepository.ChatRoomRepository;
import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChattingRoomMakeService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomAndUserRepository chatRoomAndUserRepository;
    private final UserRepository userRepository;

    public ChatRoom createChatRoom(String roomName, List<User> member){
        // 새로운 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);


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
}
