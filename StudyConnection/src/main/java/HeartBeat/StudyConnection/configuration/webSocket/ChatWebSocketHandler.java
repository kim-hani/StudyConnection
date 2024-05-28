package HeartBeat.StudyConnection.configuration.webSocket;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatMessage;
import HeartBeat.StudyConnection.repository.chatRoomRepository.ChatRoomRepository;
import HeartBeat.StudyConnection.service.chatRoomService.ChatMessageService;
import HeartBeat.StudyConnection.service.studyArticleService.StudyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, List<WebSocketSession>> chatRooms = new HashMap<>();
    private final Map<WebSocketSession, Long> sessionRoomMap = new HashMap<>();
    private final ArrayList<ChatMessage> messageBuffer = new ArrayList<>();

    private final ObjectMapper objectMapper;

    private final ChatMessageService chatMessageService;

    // 해당 채팅방이 어떤 스터디 채팅방인지
    private Long getStudyIdFromSession(WebSocketSession socketSession) {
        URI uri = socketSession.getUri();
        String path = uri.getPath(); // /chat/{studyId}
        String[] segments = path.split("/");
        return Long.valueOf(segments[segments.length]);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession socketSession) throws Exception{
        Long studyId = getStudyIdFromSession(socketSession);
        chatRooms.computeIfAbsent(String.valueOf(studyId), k -> new ArrayList<>()).add(socketSession);
        sessionRoomMap.put(socketSession, studyId);

        // 이전 메시지 로드 후 클라이언트에게 전송
        List<ChatMessage> previousMessages = chatMessageService.loadChatMessagesByStudyId(studyId);
        for (ChatMessage message : previousMessages) {
            socketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession socketSession, TextMessage message) throws Exception{
        Long studyId = sessionRoomMap.get(socketSession);
        List<WebSocketSession> sessions = chatRooms.get(studyId);

        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        chatMessage.setDateTime(LocalDateTime.now());

        // 메세지 임시 저장
        messageBuffer.add(chatMessage);

        // 메세지 전송
        if(sessions != null){
            for (WebSocketSession session : sessions){
                session.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession socketSession, CloseStatus closeStatus) throws Exception {
        chatMessageService.saveAllChatMessage(messageBuffer);

        Long studyId = sessionRoomMap.remove(socketSession);
        if (studyId != null) {
            List<WebSocketSession> sessions = chatRooms.get(studyId);
            if (sessions != null) {
                sessions.remove(socketSession);
                if (sessions.isEmpty()) {
                    chatRooms.remove(studyId);
                }
            }
        }
    }

}
