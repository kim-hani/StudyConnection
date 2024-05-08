package HeartBeat.StudyConnection.controller.studyArticleController;

import HeartBeat.StudyConnection.dto.studyArticleDto.*;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyApply;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.service.studyArticleService.StudyApplyService;
import HeartBeat.StudyConnection.service.studyArticleService.StudyArticleService;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Study Article", description = "Study Article API")
public class StudyArticleApiController {

    private final StudyArticleService studyArticleService;
    private final StudyApplyService studyApplyService;
    private final UserService userService;

    // 스터디 모집글 생성
    @PostMapping("/api/study-articles")
    @Operation(summary = "스터디 모집글 작성", description = "스터디 모집글 작성 시 사용하는 API")
    @Parameters({
            @Parameter(name = "id", description = "스터디 모집글 id (Long 타입)", example = "1"),
            @Parameter(name = "title", description = "스터디 모집글 제목", example = "자바 꽉 잡아요"),
            @Parameter(name = "content", description = "스터디 모집글 내용", example = "자바 스터디 그룹~"),
            @Parameter(name = "author", description = "스터디 모집글 작성자의 이름", example = "김간단"),
            @Parameter(name= "limit Of Participant",description = "스터디 모집 정원", example = "10")
    })
    public ResponseEntity<Long> save(@RequestBody AddStudyRequestDto requestDto){
        Long savedArticleId = studyArticleService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticleId);
    }

    // 스터디 모집글 전체 조회
    @GetMapping("/api/study-articles")
    @Operation(summary = "메인 페이지 스터디 모집글 조회", description = "메인 페이지에서 전체 스터디 모집글을 조회")
    public ResponseEntity<List<AddStudyListResponseDto>> findAllDesc() {
        List<AddStudyListResponseDto> studyList = studyArticleService.findAllDesc();

        return ResponseEntity.ok()
                .body(studyList);
    }


    // 특정 스터디 모집글 수정
    @PutMapping("/api/study-articles/{id}")
    @Parameters({
            @Parameter(name = "id", description = "스터디 모집글 id (Long 타입)", example = "1"),
            @Parameter(name = "title", description = "스터디 모집글 제목", example = "자바 꽉 잡아요"),
            @Parameter(name = "content", description = "스터디 모집글 내용", example = "자바 스터디 그룹~"),
            @Parameter(name = "author", description = "스터디 모집글 작성자의 이름", example = "김간단")
    })
    @Operation(summary = "스터디 모집글 수정", description = "스터디 모집글 수정 시 사용하는 API")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody UpdateStudyRequestDto requestDto){
        Long updatedArticleId = studyArticleService.update(id, requestDto);

        return ResponseEntity.ok()
                .body(updatedArticleId);
    }

    // 특정 스터디 모집글 조회
    @GetMapping("/api/study-articles/{id}")
    @Operation(summary = "특정 스터디 모집글 조회", description = "특정 스터디 모집글 조회 시 사용하는 API")
    public ResponseEntity<StudyResponseDto> findById(@PathVariable Long id){
        StudyResponseDto studyArticle = studyArticleService.findById(id);

        return ResponseEntity.ok()
                .body(studyArticle);
    }

    // 특정 스터디 모집글 삭제
    @DeleteMapping("/api/study-articles/{id}")
    @Operation(summary = "스터디 모집글 삭제", description = "스터디 모집글 삭제 시 사용하는 API")
    public Long delete(@PathVariable Long id){
        studyArticleService.delete(id);
        return id;
    }

    // [글 주인 외의 사용자] Apply 버튼으로 스터디 참여 신청 가능
    @PostMapping("/api/study-articles/{id}/apply")
    @Operation(summary = "스터디 가입 신청", description = "글 작성자 이외의 사용자가 스터디 가입 신청")
    public ResponseEntity<String> applyToStudy(@PathVariable Long id, StudyApplyRequestDto request){
        User user = userService.findById(request.getUserId());
        String savedApplyUserId = studyApplyService.saveApply(user.getUserId(), user.getUsername(), id);

        return ResponseEntity.ok()
                .body(savedApplyUserId);
    }

    // 글 작성자가 스터디에 신청한 신청자들을 확인
    @GetMapping("/api/study-articles/{id}/apply")
    @Operation(summary = "스터디 가입 신청자 확인", description = "글 작성자가 스터디에 신청한 신청자들의 Id, 이름을 조회")
    public ResponseEntity<List<StudyApply>> applyToStudy(@PathVariable Long id){

        return ResponseEntity.ok()
                .body(studyApplyService.showAllApplicantsId(id));
    }

    // 글 작성자가 확정된 스터디 멤버들의 ID를 보내 스터디 개설을 완료한다.
    /*@PostMapping("/api/study-articles/{id}/study-confirm")
    @Operation(summary = "스터디 가입 신청", description = "글 작성자 이외의 사용자가 스터디 가입 신청")
    public ResponseEntity<String> confirmStudy(@PathVariable Long id, ConfirmStudyRequestDto request){

        // 확정된 멤버들의 ID
        List<String> confirmedMemberId = request.getMembers();

        //

        return ResponseEntity.ok()
                .body(savedApplyUserId);
    }*/
}
