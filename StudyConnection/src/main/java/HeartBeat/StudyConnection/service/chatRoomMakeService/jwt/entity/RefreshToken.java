package HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , updatable = false)
    private Long id;

    @Column(name = "user_id",nullable = false, unique= true)
    private String userId;

    @Column(name = "refresh_token", nullable = false, length = 512)
    private String refreshToken; // 토큰값

    @Builder
    public RefreshToken(String userId,String refreshToken){
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }

}
