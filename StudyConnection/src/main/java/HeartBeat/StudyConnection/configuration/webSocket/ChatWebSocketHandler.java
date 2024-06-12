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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, Long> sessionRoomMap = new ConcurrentHashMap<>();
    private final List<ChatMessage> messageBuffer = new ArrayList<>();
    private final ChatMessageService chatMessageService;
    private final ObjectMapper objectMapper;
    private final StudyService studyService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Long studyId = getStudyIdFromSession(session);

        sessionRoomMap.put(session, studyId);
        CLIENTS.put(session.getId(), session);


        // 이전 기록 로드
        List<ChatMessage> previousMessages = chatMessageService.loadChatMessagesByStudyId(studyId);
        for (ChatMessage message : previousMessages) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long studyId = sessionRoomMap.remove(session);
        CLIENTS.remove(session.getId());

        chatMessageService.saveAllChatMessage(new ArrayList<>(messageBuffer));
        messageBuffer.clear();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long studyId = sessionRoomMap.get(session);
        String userId = session.getPrincipal().getName();

        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        chatMessage.setStudyId(studyId);
        chatMessage.setUserId(userId);
        chatMessage.setDateTime(LocalDateTime.now());
        chatMessage.setContent(message.getPayload()); // 메시지의 내용 저장

        messageBuffer.add(chatMessage);

        for (WebSocketSession clientSession : CLIENTS.values()) {
            if (sessionRoomMap.get(clientSession).equals(studyId)) {
                clientSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            }
        }
    }

    private Long getStudyIdFromSession(WebSocketSession session) {
        String uriPath = session.getUri().getPath();
        String[] segments = uriPath.split("/");
        return Long.valueOf(segments[segments.length - 1]);
    }

    private boolean isUserInStudy(WebSocketSession session, Long studyId) {
        Principal user = session.getPrincipal();
        return user != null && studyService.isUserInStudy(user.getName(), studyId);
    }
}

