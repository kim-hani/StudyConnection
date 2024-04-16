package HeartBeat.StudyConnection.studyArticle.entity;


import HeartBeat.StudyConnection.userInfo.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "studyArticle")
public class StudyArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studyId")
    private int id;        // 게시글 아이디

    @ManyToOne
    private String author;   // 작성자

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private int limitOfParticipants;    // 스터디 정원

    private boolean available;      // 참여 가능 여부

    @Column(columnDefinition = "TEXT",nullable = false)
    private String title;       // 스터디 이름

    private boolean online;

    private LocalDateTime uploadDate;

    private String comment;

    @Builder
    public StudyArticle(int id, String author, String content, int limitOfParticipants, boolean available,
                        String title, boolean online, LocalDateTime uploadDate, String comment){
        this.id = id;
        this.author = author;
        this.content = content;
        this.limitOfParticipants = limitOfParticipants;
        this.available = available;
        this.title = title;
        this.online=online;
        this.uploadDate = uploadDate;
        this.comment = comment;
    }


    public void update(String title,String content){
        this.title = title;
        this.content = content;
    }

    // 작성자 확인 메서드
    public boolean isAuthor(User user){
        return this.author.equals(user);
    }

}
