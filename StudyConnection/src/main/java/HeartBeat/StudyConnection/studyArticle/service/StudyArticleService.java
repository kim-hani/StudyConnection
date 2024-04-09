package HeartBeat.StudyConnection.studyArticle.service;

import HeartBeat.StudyConnection.studyArticle.dto.AddStudyRequestDto;
import HeartBeat.StudyConnection.studyArticle.entity.StudyArticle;
import HeartBeat.StudyConnection.studyArticle.repository.StudyArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyArticleService {
    private final StudyArticleRepository studyRepository;

    public Long addStudy(AddStudyRequestDto addStudyDto){
        StudyArticle study = new StudyArticle();
        study.setTitle(addStudyDto.getTitle());
    }

}