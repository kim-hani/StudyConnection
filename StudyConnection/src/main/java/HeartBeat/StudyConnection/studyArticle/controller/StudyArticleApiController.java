package HeartBeat.StudyConnection.studyArticle.controller;

import HeartBeat.StudyConnection.studyArticle.dto.AddStudyRequestDto;
import HeartBeat.StudyConnection.studyArticle.dto.StudyResponseDto;
import HeartBeat.StudyConnection.studyArticle.dto.UpdateStudyRequestDto;
import HeartBeat.StudyConnection.studyArticle.service.StudyArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
