package HeartBeat.StudyConnection.KimJongBin;

import HeartBeat.StudyConnection.dto.studyArticleDto.ConfirmStudyRequestDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.StudyApplicantDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.StudyApplicantsResponseDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.StudyApplyRequestDto;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyApply;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyApplyRepository;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyArticleRepository;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyRepository;
import HeartBeat.StudyConnection.repository.studyArticleRepository.UserStudyRepository;
import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudyArticleApiController_confirmStudy_test {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private StudyApplyRepository studyApplyRepository;
    @Autowired
    private UserStudyRepository userStudyRepository;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private StudyArticleRepository studyArticleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        /*
        studyApplyRepository.deleteAll();
        userStudyRepository.deleteAll();
        studyRepository.deleteAll();
        studyArticleRepository.deleteAll();
        userRepository.deleteAll();

         */
    }

    @DisplayName("applyToStudy(): 스터디에 참여 신청을 할 수 있다.")
    @Test
    void applyToStudy() throws Exception{

        // given
        final String url = "/api/study-articles/{id}/apply";

        final StudyArticle newArticle = studyArticleRepository.save(StudyArticle.builder()
                .author("010-0000-0000")
                .content("test content")
                .recruitment(true)
                .limitOfParticipants(5)
                .title("자바 테스트")
                .build());

        final User tester = userRepository.save(User.builder()
                .email("test@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-0000-0000")
                .username("tester")
                .build());

        final User applier = userRepository.save(User.builder()
                .email("applier1@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-1111-1234")
                .username("applier1")
                .build());

        final StudyApplyRequestDto requestDto = new StudyApplyRequestDto(applier.getUserId());
        System.out.println(requestDto.getUserId());
        final String requestBody = objectMapper.writeValueAsString(requestDto);
        System.out.println(requestBody);

        // when
        ResultActions result = mockMvc.perform(post(url, newArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());

        List<StudyApply> applies = studyApplyRepository.findByStudyArticleId(newArticle.getId());
        User searchedUser = userService.findByUserId(applier.getUserId());

        assertThat(applies.size()).isEqualTo(1);
        System.out.println(applies.get(0).toString());
        assertThat(applies.get(0).getUserId()).isEqualTo(applier.getUserId());
        assertThat(applies.get(0).getUserId()).isEqualTo(searchedUser.getUserId());
    }

    @DisplayName("showAllApplies(): 글 작성자는 스터디 가입 신청자를 조회할 수 있다.")
    @Test
    void showAllApplies() throws Exception{
        // given
        final String url = "/api/study-articles/{id}/apply";

        final StudyArticle newArticle = studyArticleRepository.save(StudyArticle.builder()
                .author("010-0000-0000")
                .content("test content")
                .recruitment(true)
                .limitOfParticipants(5)
                .title("자바 테스트")
                .build());

        //  작성자
        final User tester = userRepository.save(User.builder()
                .email("test@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-0000-0000")
                .username("tester")
                .build());

        final User applier1 = userRepository.save(User.builder()
                .email("applier1@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-1111-1234")
                .username("applier1")
                .build());

        final User applier2 = userRepository.save(User.builder()
                .email("applier2@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-2222-1234")
                .username("applier2")
                .build());

        // 신청자 저장
        final StudyApply studyApply1 = studyApplyRepository.save(StudyApply.builder()
                .userId(applier1.getUserId())
                .studyArticleId(newArticle.getId())
                .build());

        final StudyApply studyApply2 = studyApplyRepository.save(StudyApply.builder()
                .userId(applier2.getUserId())
                .studyArticleId(newArticle.getId())
                .build());

        List<StudyApplicantDto> studyApplicantDto = new ArrayList<>();

        studyApplicantDto.add(StudyApplicantDto.builder()
                .username(applier1.getUsername())
                .userId(applier1.getUserId())
                .email(applier1.getEmail())
                .birth(applier1.getBirth())
                .studyId(newArticle.getId())
                .build());

        studyApplicantDto.add(StudyApplicantDto.builder()
                .username(applier2.getUsername())
                .userId(applier2.getUserId())
                .email(applier2.getEmail())
                .birth(applier2.getBirth())
                .studyId(newArticle.getId())
                .build());

        final String requestBody = objectMapper.writeValueAsString(new StudyApplicantsResponseDto(studyApplicantDto));

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, newArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicants[0].userId").value(applier1.getUserId()))
                .andExpect(jsonPath("$.applicants[0].username").value(applier1.getUsername()))
                .andExpect(jsonPath("$.applicants[0].studyId").value(newArticle.getId()))
                .andExpect(jsonPath("$.applicants[1].userId").value(applier2.getUserId()))
                .andExpect(jsonPath("$.applicants[1].username").value(applier2.getUsername()))
                .andExpect(jsonPath("$.applicants[1].studyId").value(newArticle.getId()));

    }

    @DisplayName("confirmStudy(): 스터디 모집을 마감할 수 있다.")
    @Test
    void confirmStudy() throws Exception {
        // given
        final String url = "/api/study-articles/{id}/study-confirm";

        final StudyArticle newArticle = studyArticleRepository.save(StudyArticle.builder()
                .author("010-0000-0000")
                .content("test content")
                .recruitment(true)
                .limitOfParticipants(5)
                .title("자바 테스트")
                .build());

        //  작성자
        final User tester = userRepository.save(User.builder()
                .email("test@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-0000-0000")
                .username("tester")
                .build());

        final User applier1 = userRepository.save(User.builder()
                .email("applier1@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-1111-1234")
                .username("applier1")
                .build());

        final User applier2 = userRepository.save(User.builder()
                .email("applier2@naver.com")
                .birth("1999-09-09")
                .password("testerPw")
                .userId("010-2222-1234")
                .username("applier2")
                .build());

        List<String> membersId = new ArrayList<>();
        membersId.add(applier1.getUserId());
        membersId.add(applier2.getUserId());

        final String requestBody = objectMapper.writeValueAsString(ConfirmStudyRequestDto.builder()
                .studyTitle(newArticle.getTitle())
                .members(membersId)
                .build());

        // when
        ResultActions resultActions = mockMvc.perform(post(url, newArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studyName").value(newArticle.getTitle() ))
                .andExpect(jsonPath("$.membersId", containsInAnyOrder("010-1111-1234", "010-2222-1234")));



    }

}
