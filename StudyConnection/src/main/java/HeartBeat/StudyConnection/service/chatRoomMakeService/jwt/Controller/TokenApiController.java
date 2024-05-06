package HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.Controller;

import HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.dto.CreateAccessTokenRequest;
import HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.dto.CreateAccessTokenResponse;
import HeartBeat.StudyConnection.service.chatRoomMakeService.jwt.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Token Api Controller", description = "Token Api Controller")
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/access_token")
    @Parameter(name = "Access Token", description = "Refresh Token을 이용해 Access Token을 발급")
    @Operation(summary = "Access Token 발급", description = "Access Token을 발급할 때 사용하는 API")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
