package HeartBeat.StudyConnection.controller.ratingController;

import HeartBeat.StudyConnection.dto.ResponseIdDto;
import HeartBeat.StudyConnection.dto.ratingDto.UserRatingDto;
import HeartBeat.StudyConnection.service.ratingService.UserRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "User Rating", description = "User Rating API")
public class UserRatingController {

    private final UserRatingService userRatingService;

    // 스터디 모집글 생성
    @PostMapping("/api/user-ratings")
    @Operation(summary = "유저 평가 작성", description = "유저 평가 시 사용하는 API" +
            "UserId는 평가할 대상" +
            "StudyId는 스터디 Id")
    public ResponseEntity<ResponseIdDto> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRatingDto userRatingDto){
        userRatingDto.setAuthorId(userDetails.getUsername()); // 현재 로그인한 사용자의 아이디를 작성자로 설정
        Long userRatingId = userRatingService.save(userRatingDto);
        ResponseIdDto responseIdDto = new ResponseIdDto(userRatingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseIdDto);
    }


    // 특정 유저에 관한 평가글 전체 조회
    @GetMapping("/api/user-ratings/{userId}")
    @Operation(summary = "특정 유저에 관한 평가글 전체 조회", description = "특정 유저에 관한 평가글 전체 조회")
    public ResponseEntity<List<UserRatingDto>> findAllByUserId(
            @Parameter(description = "조회할 사용자의 아이디", example = "010-3507-0000")
            @PathVariable(name = "userId", required = true) String userId){
        List<UserRatingDto> userRatingList = userRatingService.findByUserId(userId);
        return ResponseEntity.ok().body(userRatingList);
    }

    // 특정 평가글 삭제
    @DeleteMapping("/api/user-ratings/{id}")
    @Operation(summary = "특정 평가글 삭제", description = "특정 평가글 삭제 시 사용하는 API\n" +
            "해당 평가id를 통해 평가 삭제 가능")
    public ResponseEntity<ResponseIdDto> delete(
            @Parameter(description = "삭제할 평가글id", example = "2")
            @PathVariable(name = "id", required = true) Long id){
        userRatingService.deleteById(id);
        ResponseIdDto responseIdDto = new ResponseIdDto(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseIdDto);
    }

    // 특정 평가글의 평점/평가글내용 수정
    @PutMapping("/api/user-ratings/{id}")
    @Operation(summary = "특정 평가글의 평점/평가글내용 수정", description = "특정 평가글의 평점/평가글내용 수정 시 사용하는 API" +
            "해당 평가id를 통해 평가 삭제 가능")
    public ResponseEntity<Long> update(
            @Parameter(description = "수정할 평가글id", example = "1")
            @PathVariable(name = "id", required = true) Long id, @RequestBody UserRatingDto userRatingDto){
        userRatingDto.setId(id);
        Long updatedArticleId = userRatingService.update(userRatingDto);
        ResponseIdDto responseIdDto = new ResponseIdDto(updatedArticleId);
        return ResponseEntity.ok().body(updatedArticleId);
    }
}
