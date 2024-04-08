package HeartBeat.StudyConnection.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    //user 엔티티와 연결.
    // ChatRoomAndUser가 연결 테이블
    @ManyToMany(mappedBy = "chatRooms")
    private Set<User> users = new HashSet<>();


}
