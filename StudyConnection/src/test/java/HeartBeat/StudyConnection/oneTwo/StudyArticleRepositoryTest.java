package HeartBeat.StudyConnection.oneTwo;


import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyArticleRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyArticleRepositoryTest extends StudyArticle{

    @Autowired
    StudyArticleRepository studyArticleRepository;

    @After // 단위 테스트 끝난 이후
    public void cleanup() {
        studyArticleRepository.deleteAll();
    }

    @Test
    public void StudyArticleSave_findAll() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        /* save 메서드로 insert/update 쿼리 실행 : id 값 있으면 update, 없으면 insert */
        studyArticleRepository.save(StudyArticle.builder() // .필드(값) ... build()
                .title(title)
                .content(content)
                .id(5135L)
                .author("test@gmail.com")
                .build());

        //when
        List<StudyArticle> studyArticleList = studyArticleRepository.findAll();

        //then
        StudyArticle studyArticle = studyArticleList.get(0);
        assertThat(studyArticle.getTitle()).isEqualTo(title);
        assertThat(studyArticle.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity() {
        //given
        LocalDateTime now = LocalDateTime.of(2024,4,20,12,0,0);
        studyArticleRepository.save(StudyArticle.builder()
                .title("title")
                .id(5135L)
                .content("content")
                .author("author")
                .build());

        //when
        List<StudyArticle> studyArticleList = studyArticleRepository.findAll();

        //then
        StudyArticle studyArticle = studyArticleList.get(0);

        System.out.println(">>>>>> createdDate = " + studyArticle.getCreatedAt()+ ", modifiedDate = " + studyArticle.getUpdatedAt());

        assertThat(studyArticle.getCreatedAt()).isAfter(now);
        assertThat(studyArticle.getUpdatedAt()).isAfter(now);
    }
}
