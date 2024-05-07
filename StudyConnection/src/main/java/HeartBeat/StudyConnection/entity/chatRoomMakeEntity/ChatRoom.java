package HeartBeat.StudyConnection.entity.chatRoomMakeEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "chatRoomAndUser.chatRoom")
    private Set<ChatRoomAndUser> users = new HashSet<>();
}
