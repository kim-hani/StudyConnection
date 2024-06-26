package HeartBeat.StudyConnection.entity.userInfoEntity;

import HeartBeat.StudyConnection.entity.commentEntity.Comment;
import HeartBeat.StudyConnection.entity.rating.UserRating;
import HeartBeat.StudyConnection.entity.studyArticleEntity.UserStudy;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User implements UserDetails {
    // 사용자의 전화번호가 ID
    @Id
    private String userId;

    // 사용자의 패스워드
    @Column(name = "password")
    private String password;

    // 사용자의 생년월일
    @Column(name = "birth")
    private String birth; // yyyy-MM-dd

    // 사용자의 이름
    @Column(name = "username")
    private String username;

    // 사용자의 이메일
    @Column(name = "email", unique = true)
    private String email;

    // 사용자의 스터디
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Set<UserStudy> userStudies = new HashSet<>();

    @OneToMany(mappedBy =  "user",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();


    // 빌더 패턴
    @Builder
    public User(String userId, String password, String birth, String username, String email){
        this.userId = userId;
        this.password = password;
        this.birth = birth;
        this.username = username;
        this.email = email;
    }


    // UserDetails 구현
    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        // 아직 만료되지 않음.
        // 회원 가입 후 탈퇴 전까지 만료 X. 추후 정책 변동에 따라 수정 가능.
        return true;
    }

    @Override // 계정 잠금 여부
    public boolean isAccountNonLocked() {
        // 아직 계정 잠금 X.
        // 추후 정책 변동에 따라 수정 가능.
        return true;
    }

    @Override // 패스워드 만료 여부
    public boolean isCredentialsNonExpired() {
        // 아직 계정 만료 X.
        // 추후 정책 변동에 따라 수정 가능.
        return true;
    }

    @Override // 계정 사용 가능 여부 반환
    public boolean isEnabled() {
        // 추후 정책 변동에 따라 수정 가능.
        return true;
    }
}