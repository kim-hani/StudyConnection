package HeartBeat.StudyConnection.controller.chattingController;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoom;
import HeartBeat.StudyConnection.service.chatRoomService.ChattingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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
