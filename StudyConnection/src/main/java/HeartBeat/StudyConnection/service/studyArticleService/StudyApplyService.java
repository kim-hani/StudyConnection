package HeartBeat.StudyConnection.service.studyArticleService;

import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyApply;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyApplyService {
    private final StudyApplyRepository studyApplyRepository;

    public String saveApply(String userId, String userName, Long studyArticleId){
        StudyApply savedApply = studyApplyRepository.save(StudyApply.builder()
                .userId(userId)
                .username(userName)
                .studyArticleId(studyArticleId)
                .build());

        return savedApply.getUserId();
    }

    public List<StudyApply> showAllApplicantsId(Long studyId){
        List<StudyApply> studyApplies = studyApplyRepository.findByStudyArticleId(studyId);
        return studyApplies;
    }
}
