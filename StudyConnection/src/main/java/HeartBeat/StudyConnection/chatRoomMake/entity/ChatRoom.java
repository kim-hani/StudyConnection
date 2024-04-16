package HeartBeat.StudyConnection.chatRoomMake.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoomId", updatable = false)
    private Long id;

    @Column(name = "roomName")
    private String roomName;
}
