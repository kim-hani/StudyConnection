package HeartBeat.StudyConnection.service;

import HeartBeat.StudyConnection.entity.ChatRoom;
import HeartBeat.StudyConnection.entity.User;
import HeartBeat.StudyConnection.repository.ChatRoomRepository;
import HeartBeat.StudyConnection.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChattingRoomMakeService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoom createChatRoom(String roomName, List<String> membersId){
        // 새로운 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomName);

        // User 엔티티들을 찾아 채팅방에 추가
        Set<String> receivedUserIds = new HashSet<>(membersId);

        // 사용자 엔티티들을 찾아서 채팅방에 추가
        Set<User> users = new HashSet<>();
        for(String userId: receivedUserIds){
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException(userId + "의 회원을 찾을 수 없습니다."));
            users.add(user);
        }

        // 채팅방 저장
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
}
