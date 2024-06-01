package HeartBeat.StudyConnection.entity.chatRoomEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoomId", updatable = false)
    private Long id;

    @Column(name = "roomName")
    private String roomName;

    @Column(name = "studyId")
    private Long studyId;

    @Column(name = "participant")
    private String userId;

    @Builder
    public ChatRoom(String roomName, Long studyId, String userId){
        this.roomName = roomName;
        this.studyId = studyId;
        this.userId = userId;
    }
}
