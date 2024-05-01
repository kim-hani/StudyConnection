package HeartBeat.StudyConnection.controller.user;

import HeartBeat.StudyConnection.configuration.jwt.service.RefreshTokenService;
import HeartBeat.StudyConnection.configuration.jwt.service.TokenService;
import HeartBeat.StudyConnection.dto.loginDto.UserLoginRequest;
import HeartBeat.StudyConnection.dto.loginDto.UserLoginResponse;
import HeartBeat.StudyConnection.dto.signUpDto.AddUserRequest;
import HeartBeat.StudyConnection.dto.signUpDto.AddUserResponse;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API", description = "User 기능 관련 API")
public class UserApiController {

    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    @Operation(summary = "사용자 로그인", description = "로그인 시 사용하는 API")
    @Parameters({
            @Parameter(name = "id", description = "사용자의 ID (전화번호)", example = "010-0000-0000"),
            @Parameter(name = "password", description = "사용자의 Password", example = "test1234!"),
    })
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        User user = userService.findById(userLoginRequest.getId());
        Authentication authentication = authenticate(userLoginRequest.getId(), userLoginRequest.getPassword());

        // 로그인 시 리프레시, 액세스 토큰 생성
        String refreshToken = refreshTokenService.createNewRefreshToken(user);
        String accessToken = tokenService.createNewAccessToken(refreshToken);

        return ResponseEntity.ok(new UserLoginResponse().builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
    }

    private Authentication authenticate(String userId, String password){
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, password);
        return authenticationManager.authenticate(authentication);
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
        userService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddUserResponse(request.toEntity()));
    }
}