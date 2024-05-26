package HeartBeat.StudyConnection.entity.chatRoomEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", updatable = false)
    private Long id;

    @Column(name = "study_id", updatable = false)
    private Long studyId;

    @Column(name = "user_id", updatable = false)
    private String userId;

    @Column(name = "datetime", updatable = false)
    private LocalDateTime dateTime;

    @Column(name = "content", updatable = false)
    private String content;

    @Builder
    public ChatMessage(Long studyId, String userId, LocalDateTime dateTime, String content){
        this.studyId = studyId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.content = content;
    }

}
