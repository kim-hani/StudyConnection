package HeartBeat.StudyConnection.controller.studyArticleController;

import HeartBeat.StudyConnection.controller.dto.studyArticleDto.AddStudyListResponseDto;
import HeartBeat.StudyConnection.controller.dto.studyArticleDto.AddStudyRequestDto;
import HeartBeat.StudyConnection.controller.dto.studyArticleDto.StudyResponseDto;
import HeartBeat.StudyConnection.controller.dto.studyArticleDto.UpdateStudyRequestDto;
import HeartBeat.StudyConnection.service.studyArticleService.StudyArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudyArticleApiController {

    private final StudyArticleService studyArticleService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody AddStudyRequestDto requestDto){
        return studyArticleService.save(requestDto);
    }

    @PostMapping("/api/v1/posts/{id}")
    public int update(@PathVariable Integer id, @RequestBody UpdateStudyRequestDto requestDto){
        return studyArticleService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public StudyResponseDto findById(@PathVariable int id){
        return studyArticleService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public int delete(@PathVariable int id){
        studyArticleService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts")
    public List<AddStudyListResponseDto> findAllDesc() {
        return studyArticleService.findAllDesc();
    }
}
