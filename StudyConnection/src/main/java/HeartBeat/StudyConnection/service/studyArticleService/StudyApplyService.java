package HeartBeat.StudyConnection.service.studyArticleService;

import HeartBeat.StudyConnection.dto.studyArticleDto.StudyApplicantDto;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyApply;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyApplyRepository;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyApplyService {
    private final StudyApplyRepository studyApplyRepository;
    private final UserService userService;

    @Transactional
    public StudyApply saveApply(String userId, Long studyArticleId){

        StudyApply savedApply = studyApplyRepository.save(StudyApply.builder()
                .userId(userId)
                .studyArticleId(studyArticleId)
                .build());


        return savedApply;
    }

    public List<StudyApply> showAllApplicantsId(Long studyArticleId){
        List<StudyApply> studyApplies = studyApplyRepository.findByStudyArticleId(studyArticleId);

        for (StudyApply apply : studyApplies) {
            if (apply.getUserId() == null) {
                throw new NullPointerException("User not found for userId: " + apply.getUserId());
            }
        }

        return studyApplies;
    }

    //
    public List<StudyApplicantDto> toApplyDtoList (List<StudyApply> entities){
        List<StudyApplicantDto> applies = new ArrayList<>();
        for(StudyApply entity : entities){
            User user = userService.findByUserId(entity.getUserId());
            applies.add(StudyApplicantDto.builder()
                    .username(user.getUsername())
                    .userId(user.getUserId())
                    .birth(user.getBirth())
                    .email(user.getEmail())
                    .studyId(entity.getStudyArticleId())
                    .build());
        }
        return applies;
    }

}
