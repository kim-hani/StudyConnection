package HeartBeat.StudyConnection.controller.user;

import HeartBeat.StudyConnection.configuration.jwt.TokenProvider;
import HeartBeat.StudyConnection.configuration.jwt.entity.RefreshToken;
import HeartBeat.StudyConnection.configuration.jwt.service.RefreshTokenService;
import HeartBeat.StudyConnection.configuration.jwt.service.TokenService;
import HeartBeat.StudyConnection.dto.UserInformationResponse;
import HeartBeat.StudyConnection.dto.loginDto.UserLoginRequest;
import HeartBeat.StudyConnection.dto.loginDto.UserLoginResponse;
import HeartBeat.StudyConnection.dto.signUpDto.AddUserRequest;
import HeartBeat.StudyConnection.dto.signUpDto.AddUserResponse;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.service.studyArticleService.StudyService;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API", description = "User 기능 관련 API")
public class UserApiController {

    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final StudyService studyService;

    @PostMapping("/api/login")
    @Operation(summary = "사용자 로그인", description = "로그인 시 사용하는 API")
    @Parameters({
            @Parameter(name = "id", description = "사용자의 ID (전화번호)", example = "010-0000-0000"),
            @Parameter(name = "password", description = "사용자의 Password", example = "test1234!"),
    })
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){

        User user = userService.findByUserId(userLoginRequest.getId());
        System.out.println(user.getUserId() + " " + user.getPassword());

        // 로그인 인증 절차
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getId(), userLoginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 절차 통과 후 객체 조회


        // 로그인 시 리프레시, 액세스 토큰 생성
        Optional<RefreshToken> refreshTokenSearch = refreshTokenService.findByUserId(user.getUserId());

        RefreshToken refreshToken;
        String refreshTokenValue;
        String accessToken;

        // refreshTokenSearch 존재할 때 -> 해당 사용자의 refresh token 존재.
        if (refreshTokenSearch.isPresent()) {
            // 저장된 리프레시 토큰 사용
            refreshToken = refreshTokenSearch.get();
            accessToken = tokenService.createNewAccessToken(refreshToken.getRefreshToken());
        } else {
            // 새로운 리프레시 토큰 생성
            refreshTokenValue = tokenProvider.createNewRefreshToken(user);
            refreshToken = refreshTokenService.save(user.getUserId(), refreshTokenValue);
            accessToken = tokenService.createNewAccessToken(refreshTokenValue);
        }

        return ResponseEntity.ok()
                .body(UserLoginResponse.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getRefreshToken())
                        .build());
    }


    @PostMapping("/api/signup")
    @Operation(summary = "사용자 회원 가입", description = "회원 가입 시 사용하는 API")
    @Parameters({
            @Parameter(name = "id", description = "사용자가 사용할 ID (전화번호)", example = "010-0000-0000"),
            @Parameter(name = "password", description = "사용자가 사용할 Password", example = "test1234!"),
            @Parameter(name = "username", description = "사용자의 이름", example = "테스터김"),
            @Parameter(name = "email", description = "사용자가 사용할 email", example = "test123@naver.com"),
            @Parameter(name = "birth", description = "사용자의 생년월일", example = "1999-09-09")
    })
    public ResponseEntity<AddUserResponse> signup(@RequestBody AddUserRequest request){
        String newUserId = request.getUserId();
        String resultMessage;

        
        // 중복된 아이디 처리
        if(userService.findByUserId(newUserId) != null){
            resultMessage = "Error: ID duplicate";
            return  ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AddUserResponse("Failed", "Failed", resultMessage));
        }

        // 중복되지 않았을 때 회원 가입
        User savedUser = userService.save(request);
        String savesUserId = savedUser.getUserId();
        String savedUserName = savedUser.getUsername();
        resultMessage = "sign-up success";

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddUserResponse(savesUserId, savedUserName, resultMessage));
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 사용자의 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 현재 세션을 무효화하고 인증 정보를 삭제
            new SecurityContextLogoutHandler().logout(request, null, authentication);
            return ResponseEntity.ok()
                    .body("Logged out successfully");
        }

        return ResponseEntity.ok()
                .body("Logged out failed");
    }

    // 사용자의 정보를 전달
    @GetMapping("/api/userinfo/{userId}")
    @Parameter(name = "userId", description = "조회하려는 사용자의 id", example = "010-0000-0000")
    @Operation(summary = "사용자의 정보를 조회", description = "사용자의 정보를 조회한다. 본인의 정보도 조회할 수 있다.")
    public ResponseEntity<UserInformationResponse> showUserInfo(@PathVariable String userId){
        User searchUser = userService.findByUserId(userId);
        LocalDate date = LocalDate.now();
        int thisYear = Integer.parseInt(String.valueOf(date.getYear())) - Integer.parseInt(searchUser.getBirth().split("-")[0]) + 1;

        // 사용자 없을 때
        if(userId == null){
            return ResponseEntity.notFound()
                    .build();
        }

        return ResponseEntity.ok()
                .body(UserInformationResponse.builder()
                        .username(searchUser.getUsername())
                        .age(String.valueOf(thisYear))
                        .email(searchUser.getEmail())
                        .userId(searchUser.getUserId())
                        .studyList(studyService.loadUserStudies(userId))
                        .build());
    }
}
