package HeartBeat.StudyConnection.controller.chattingController;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoom;
import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoomAndUser;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.service.chatRoomService.ChattingRoomService;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingRoomService chattingRoomService;

    @GetMapping("/api/chats")
    public ResponseEntity<List<ChatRoom>> getUserChatRooms (Principal principal){
        String userId = principal.getName();
        List<ChatRoom> chatRooms = chattingRoomService.findChatRoomsByUserId(userId);

        return ResponseEntity.ok()
                .body(chatRooms);
    }
}
