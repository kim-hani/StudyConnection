package HeartBeat.StudyConnection.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String userId; // 사용자의 전화번호

    @Column(name = "password")
    private String password;

    @Column(name = "birth")
    private String birth; // yyyy-MM-dd

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_chat_room",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_room_id"))
    private Set<ChatRoom> chatRooms = new HashSet<>();

    // 빌더 패턴
    @Builder
    public User(String userId, String password, String birth, String username, String email){
        this.userId = userId;
        this.password = password;
        this.birth = birth;
        this.username = username;
        this.email = email;
    }
}
