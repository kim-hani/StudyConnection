package HeartBeat.StudyConnection.controller.studyArticleController;

import HeartBeat.StudyConnection.dto.studyArticleDto.AddStudyListResponseDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.AddStudyRequestDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.StudyResponseDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.UpdateStudyRequestDto;
import HeartBeat.StudyConnection.service.studyArticleService.StudyArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Study Article", description = "Study Article API")
public class StudyArticleApiController {

    private final StudyArticleService studyArticleService;

    @PostMapping("/api/v1/posts")
    @Operation(summary = "스터디 모집글 작성", description = "스터디 모집글 작성 시 사용하는 API")
    @Parameters({
            @Parameter(name = "id", description = "스터디 모집글 id (Long 타입)", example = "1"),
            @Parameter(name = "title", description = "스터디 모집글 제목", example = "자바 꽉 잡아요"),
            @Parameter(name = "content", description = "스터디 모집글 내용", example = "자바 스터디 그룹~"),
            @Parameter(name = "author", description = "스터디 모집글 작성자의 이름", example = "김간단")
    })
    public Long save(@RequestBody AddStudyRequestDto requestDto){
        return studyArticleService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    @Parameters({
            @Parameter(name = "id", description = "스터디 모집글 id (Long 타입)", example = "1"),
            @Parameter(name = "title", description = "스터디 모집글 제목", example = "자바 꽉 잡아요"),
            @Parameter(name = "content", description = "스터디 모집글 내용", example = "자바 스터디 그룹~"),
            @Parameter(name = "author", description = "스터디 모집글 작성자의 이름", example = "김간단")
    })
    @Operation(summary = "스터디 모집글 수정", description = "스터디 모집글 수정 시 사용하는 API")
    public int update(@PathVariable Integer id, @RequestBody UpdateStudyRequestDto requestDto){
        return studyArticleService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    @Operation(summary = "특정 스터디 모집글 조회", description = "특정 스터디 모집글 조회 시 사용하는 API")
    public StudyResponseDto findById(@PathVariable int id){
        return studyArticleService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    @Operation(summary = "메인 페이지 스터디 모집글 조회", description = "메인 페이지에서 전체 스터디 모집글을 조회")
    public int delete(@PathVariable int id){
        studyArticleService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts")
    public List<AddStudyListResponseDto> findAllDesc() {
        return studyArticleService.findAllDesc();
    }
}
