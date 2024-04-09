package HeartBeat.StudyConnection.studyArticle.controller;

import HeartBeat.StudyConnection.studyArticle.dto.AddStudyRequestDto;
import HeartBeat.StudyConnection.studyArticle.service.StudyArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudyArticleApiController {

    private final StudyArticleService studyArticleService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody AddStudyRequestDto studyRequestDto )

}
