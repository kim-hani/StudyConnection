package HeartBeat.StudyConnection.KimJongBin;

import HeartBeat.StudyConnection.configuration.WebSecurityConfig;
import HeartBeat.StudyConnection.configuration.jwt.service.RefreshTokenService;
import HeartBeat.StudyConnection.configuration.jwt.service.TokenService;
import HeartBeat.StudyConnection.dto.loginDto.UserLoginRequest;
import HeartBeat.StudyConnection.dto.signUpDto.AddUserRequest;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    protected UserService userService;
    @Autowired
    protected TokenService tokenService;

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        userRepository.deleteAll();
    }

    @DisplayName("signup: 사용자가 회원 가입을 할 수 있다.")
    @Test
    public void signup() throws Exception{
        // given
        final String url = "/api/signup";
        final String userId = "010-9999-9999";
        final String userPw = "testPassword";
        final String userEmail = "test1234@naver.com";
        final String userBirth = "1999-09-09";
        final String username = "tester";

        final AddUserRequest request = new AddUserRequest(
                userId,
                userPw,
                userEmail,
                userBirth,
                username
        );

        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getUserId()).isEqualTo(userId);
        assertThat(users.get(0).getUsername()).isEqualTo(username);
        assertThat(users.get(0).getEmail()).isEqualTo(userEmail);
        assertThat(users.get(0).getBirth()).isEqualTo(userBirth);
        assertThat(users.get(0).getPassword()).isNotEmpty();

    }

    @DisplayName("login: 사용자 로그인에 성공할 수 있다.")
    @Test
    public void login() throws Exception{
        // given
        final String url = "/api/login";
        final String userId = "010-9999-9999";
        final String userPw = "testPassword";
        final String userEmail = "test1234@naver.com";
        final String userBirth = "1999-09-09";
        final String username = "tester";


        final User user = userRepository.save(User.builder()
                .userId(userId)
                .username(username)
                .birth(userBirth)
                .email(userEmail)
                .password(userPw)
                .build());

        final UserLoginRequest request = new UserLoginRequest(userId, userPw);
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());

    }
}
