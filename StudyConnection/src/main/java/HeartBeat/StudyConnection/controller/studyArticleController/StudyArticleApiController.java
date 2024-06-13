package HeartBeat.StudyConnection.controller.studyArticleController;


import HeartBeat.StudyConnection.configuration.jwt.AppAuthentication;
import HeartBeat.StudyConnection.dto.studyArticleDto.*;
import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyApply;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.dto.ResponseIdDto;
import HeartBeat.StudyConnection.dto.commentDto.request.RequestCreateCommentDto;
import HeartBeat.StudyConnection.dto.commentDto.response.SummarizedCommentDto;
import HeartBeat.StudyConnection.role.UserAuth;
import HeartBeat.StudyConnection.service.chatRoomService.ChattingRoomService;
import HeartBeat.StudyConnection.service.commentService.CommentService;
import HeartBeat.StudyConnection.service.studyArticleService.StudyApplyService;
import HeartBeat.StudyConnection.service.studyArticleService.StudyArticleService;
import HeartBeat.StudyConnection.service.studyArticleService.StudyService;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Study Article", description = "Study Article API")
public class StudyArticleApiController {

    private final StudyArticleService studyArticleService;
    private final StudyApplyService studyApplyService;
    private final UserService userService;
    private final CommentService commentService;
    private final StudyService studyService;
    private final ChattingRoomService chattingRoomMakeService;


    // 스터디 모집글 생성
    @PostMapping("/api/study-articles")
    @Operation(summary = "스터디 모집글 작성", description = "스터디 모집글 작성 시 사용하는 API")
    @Parameters({
            @Parameter(name = "id", description = "스터디 모집글 id (Long 타입)", example = "1"),
            @Parameter(name = "title", description = "스터디 모집글 제목", example = "자바 꽉 잡아요"),
            @Parameter(name = "content", description = "스터디 모집글 내용", example = "자바 스터디 그룹~"),
            @Parameter(name = "authorId", description = "스터디 모집글 작성자의 Id", example = "010-0000-0000"),
            @Parameter(name = "authorName", description = "스터디 모집글 작성자의 이름", example = "김단국"),
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

    // 스터디 모집글 전체 조회
    @GetMapping("/api/study-articles-paging")
    @Operation(summary = "메인 페이지 스터디 모집글 조회(페이징)", description = "메인 페이지에서 전체 스터디 모집글을 조회(페이징)")
    public ResponseEntity<Page<AddStudyListResponseDto>> findAllPaging(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "createdAt", value = "orderby") String criteria,
            @RequestParam(required = false, defaultValue = "DESC", value = "sort") String sort) {
        Page<AddStudyListResponseDto> studyPage = studyArticleService.findAllPaging(pageNo, criteria, sort);

        return ResponseEntity.ok().body(studyPage);
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
        StudyArticle studyArticle = studyArticleService.findById(id);
        Study study = studyService.findByStudyId(id);

        if(study == null){
            // 스터디 확정 전이니까 available 은 true 여도 상관 X
            return ResponseEntity.ok()
                    .body(new StudyResponseDto(studyArticle, Boolean.TRUE));
        }

        return ResponseEntity.ok()
                .body(new StudyResponseDto(studyArticle, study.getAvailable()));
    }

    // 특정 스터디 모집글 삭제
    @DeleteMapping("/api/study-articles/{id}")
    @Operation(summary = "스터디 모집글 삭제", description = "스터디 모집글 삭제 시 사용하는 API")
    public Long delete(@PathVariable Long id){
        studyArticleService.delete(id);
        return id;
    }

    //////////////////////////////////////////////////////
    // [글 주인 외의 사용자] Apply 버튼으로 스터디 참여 신청 가능
    @PostMapping("/api/study-articles/{id}/apply")
    @Parameter(name = "userId", description = "스터디 신청자 본인의 id", example = "010-0000-0000")
    @Operation(summary = "스터디 가입 신청", description = "글 작성자 이외의 사용자가 스터디 가입 신청")
    public ResponseEntity<String> applyToStudy(@PathVariable Long id, @RequestBody StudyApplyRequestDto request){

        // 스터디 모집글 조회 -> 있는지 유무 확인
        StudyArticle searchedArticle = studyArticleService.findById(id);

        if(searchedArticle == null){
            return ResponseEntity.notFound()
                    .build();
        }

        // 스터디 게시글 존재할 때
        // 이니 지원했는지 여부 확인
        List<StudyApply> studyApplies = studyApplyService.showAllApplicantsId(id);
        if(studyApplies != null){
            for(StudyApply studyApply : studyApplies){
                if(studyApply.getUserId() == request.getUserId()){
                    return ResponseEntity.badRequest()
                            .body("이미 신청한 스터디입니다.");
                }
            }
        }

        // 처음 지원할 때
        StudyApply savedApplyUser= studyApplyService.saveApply(request.getUserId(), id);

        return ResponseEntity.ok()
                .body(savedApplyUser.getUserId());
    }

    // 글 작성자가 스터디에 신청한 신청자들을 확인
    @GetMapping("/api/study-articles/{id}/apply")
    @Operation(summary = "스터디 가입 신청자 확인", description = "글 작성자가 스터디에 신청한 신청자들의 Id, 이름을 조회")
    public ResponseEntity<StudyApplicantsResponseDto> showAllApplies(@PathVariable Long id){

        StudyArticle searchedArticle = studyArticleService.findById(id);

        if(searchedArticle == null){
            return ResponseEntity.notFound()
                    .build();
        }

        // 문제
        List<StudyApply> applies = studyApplyService.showAllApplicantsId(id);

        System.out.println();
        System.out.println("==========================");
        for(StudyApply studyApply : applies){
            System.out.println();
            System.out.println(studyApply.toString());
            System.out.println();
        }
        System.out.println("==========================");
        System.out.println();

        return ResponseEntity.ok()
                .body(new StudyApplicantsResponseDto(studyApplyService.toApplyDtoList(applies)));
    }

    // [확정버튼] 글 작성자가 확정된 스터디 멤버들의 ID를 보내 스터디 개설을 완료한다.
    @PostMapping("/api/study-articles/{id}/study-confirm")
    @Parameters({
            @Parameter(name = "members", description = "스터디 신청자 userId 목록"),
            @Parameter(name = "studyTitle", description = "스터디 모집글 제목", example = "자바 꽉 잡아요"),
    })
    @Operation(summary = "스터디 모집 확정", description = "글 작성자 이외의 사용자가 스터디 가입 신청")
    public ResponseEntity<ConfirmStudyResponseDto> confirmStudy(@PathVariable Long id, @RequestBody ConfirmStudyRequestDto request){

        // 해당 스터디 모집글 조회
        StudyArticle searchedArticle = studyArticleService.findById(id);
        if(searchedArticle == null){
            return ResponseEntity.notFound()
                    .build();
        }

        System.out.println();
        System.out.println("==========================");
        System.out.println("<<<<<<" + searchedArticle.getTitle() + ">>>>>");
        System.out.println("<<<<<<" + searchedArticle.getId() + ">>>>>");
        System.out.println("<<<<<<" + searchedArticle.getAuthorId() + ">>>>>");
        System.out.println("==========================");
        System.out.println();

        System.out.println();
        System.out.println("==========================");
        System.out.println("<<<<<<" + request.getStudyTitle() + ">>>>>");
        for(String userId : request.getMembers()){
            System.out.println(userId);
        }
        System.out.println("==========================");
        System.out.println();

        // 확정된 멤버들을 저장할 리스트
        List<User> confirmedUser = new ArrayList<>();

        // 받은 멤버들의 ID로 User 객체 조회 후 리스트에 저장
        for(String userId : request.getMembers()){
            confirmedUser.add(userService.findByUserId(userId));
        }

        // 확정 스터디 저장
        Study confirmedStudy = studyService.saveStudy(request.getStudyTitle(), confirmedUser, id);

        // 스터디 모집 마감 -> available 수정
        studyArticleService.updateRecruitmentToFalse(searchedArticle.getId());

        // 확정된 스터디 채팅방 생성
        String chatRoomName = "[" + request.getStudyTitle() + "'s chat room]";
        chattingRoomMakeService.createChatRoom(chatRoomName, confirmedUser, id);

        return ResponseEntity.ok()
                .body(ConfirmStudyResponseDto.builder()
                        .studyName(request.getStudyTitle())
                        .chattingRoomName(chatRoomName)
                        .membersId(request.getMembers())
                        .build());
    }

    // 스터디 종료
    @PutMapping("/api/study-end/{id}")
    @Parameters({
            @Parameter(name = "study Id", description = "종료하려는 스터디의 아이디")
    })
    @Operation(summary = "스터디 진행이 모두 완료", description = "스터디가 끝남")
    public ResponseEntity<Void> endStudy(@PathVariable Long id){
        Study searchedStudy = studyService.findByStudyId(id);

        if(searchedStudy == null){
            return ResponseEntity.notFound()
                    .build();
        }

        studyService.setAvailableToFalse(searchedStudy);

        return ResponseEntity.ok().build();
    }
    // 확정된 스터디 검색
    @GetMapping("/api/study/{id}")
    @Operation(summary = "확정된 스터디 확인", description = "확정된 스터디 확인")
    public ResponseEntity<TesStudyRequestDto> showConfirmedStudy(@PathVariable Long id){

        StudyArticle searchedArticle = studyArticleService.findById(id);

        if(searchedArticle == null){
            return ResponseEntity.notFound()
                    .build();
        }
        Study study = studyService.findByStudyId(id);
        return ResponseEntity.ok()
                .body(new TesStudyRequestDto(study));
    }
    //////////////////////////////////////////////////////

    /**
     * 모든 댓글을 조회합니다.
     */
    @GetMapping("/api/comments/{studyArticleId}")
    @Operation(summary = "모든 댓글 조회", description = "모든 댓글 조회 시 사용하는 API")
    public List<SummarizedCommentDto> listComments(@PathVariable Long studyArticleId) {
        return commentService.list(studyArticleId);
    }

    /**
     * 게시글에 댓글을 생성합니다.
     **/
    @PostMapping("/api/comment/{studyArticleId}")
    @Operation(summary = "게시글에 댓글 생성", description = "게시글에 댓글 생성 시 사용하는 API")
    /*@UserAuth
    public ResponseIdDto createComment(AppAuthentication auth,
                                       @PathVariable Long studyArticleId,
                                       @Valid @RequestBody RequestCreateCommentDto dto) {
        Long result = commentService.create(studyArticleId, auth.getUserId(), dto.getContent());
        return new ResponseIdDto(result);
    }*/
    public ResponseIdDto createComment(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable(name="studyArticleId", required = true) Long studyArticleId,
                                       @Valid @RequestBody RequestCreateCommentDto dto) {
        Long result = commentService.create(studyArticleId, userDetails.getUsername(), dto.getContent());
        return new ResponseIdDto(result);
    }

    /**
     * 댓글을 수정합니다.
     * <p>대댓글도 수정할 수 있습니다.</p>
     */
    @PatchMapping("/api/comment/{commentId}")
    @Operation(summary = "댓글,대댓글 수정", description = "게시글의 댓글 수정 시 사용하는 API")
    /*@UserAuth
    public void editComment(AppAuthentication auth,
                            @PathVariable Long commentId,
                            @Valid @RequestBody RequestCreateCommentDto dto) {
        commentService.edit(commentId, auth.getUserId(), dto.getContent());
    }*/
    public void editComment(@AuthenticationPrincipal UserDetails userDetails,
                            @PathVariable(name="commentId", required = true) Long commentId,
                            @Valid @RequestBody RequestCreateCommentDto dto) {
        commentService.edit(commentId, userDetails.getUsername(), dto.getContent());
    }

    /**
     * 댓글을 삭제합니다.
     * <p>대댓글도 삭제할 수 있습니다.</p>
     */
    @DeleteMapping("/api/comment/{commentId}")
    @Operation(summary = "댓글,대댓글 삭제", description = "댓글 삭제 시 사용하는 API")
    /*@UserAuth
    public void deleteComment(AppAuthentication auth,
                              @PathVariable Long commentId) {
        commentService.delete(commentId, auth.getUserId());
    }*/
    public void deleteComment(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable(name="commentId", required = true) Long commentId) {
        commentService.delete(commentId, userDetails.getUsername());
    }

    /**
     * 대댓글을 생성합니다.
     *
     * @param commentId   댓글 ID
     */
    @PostMapping("/api/reply/{commentId}")
    @Operation(summary = "대댓글 생성", description = "대댓글 생성 시 사용하는 API")
    /*@UserAuth
    public ResponseIdDto createReply(AppAuthentication auth,
                                     @PathVariable Long commentId,
                                     @Valid @RequestBody RequestCreateCommentDto dto) {
        Long result = commentService.createReply(commentId, auth.getUserId(), dto.getContent());
        return new ResponseIdDto(result);
    }*/
    public ResponseIdDto createReply(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable(name="commentId", required = true) Long commentId,
                                     @Valid @RequestBody RequestCreateCommentDto dto) {
        Long result = commentService.createReply(commentId, userDetails.getUsername(), dto.getContent());
        return new ResponseIdDto(result);
    }
}
