package HeartBeat.StudyConnection.service.studyArticleService;

import HeartBeat.StudyConnection.dto.UserWithStudyDto.UserNameIdResponse;
import HeartBeat.StudyConnection.dto.UserWithStudyDto.UserStudiesResponse;
import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import HeartBeat.StudyConnection.entity.studyArticleEntity.UserStudy;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyRepository;
import HeartBeat.StudyConnection.repository.studyArticleRepository.UserStudyRepository;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserStudyRepository userStudyRepository;
    private final UserService userService;

    @Transactional
    public Study saveStudy(String studyTitle, List<User> users, Long studyId){
        // 새로운 Study 확정
        Study study = new Study();
        Set<UserStudy> userStudies = new HashSet<>();

        study.setStudyName(studyTitle);
        study.setStudyId(studyId);
        study.setAvailable(true);

        // Study 엔티티 저장
        Study savedStudy = studyRepository.save(study);

        // UserStudy 엔티티 생성 및 저장
        for(User user : users){
            UserStudy userStudy = new UserStudy();
            userStudy.setUser(user);
            userStudy.setStudy(savedStudy); // 영속된 Study 객체를 참조함
            userStudies.add(userStudy);
        }
        userStudyRepository.saveAll(userStudies);
        savedStudy.setUserStudies(userStudies);
        System.out.println(savedStudy);

        return savedStudy;
    }

    @Transactional
    public void setAvailableToFalse(Study endStudy){
        endStudy.setAvailable(false);
        studyRepository.save(endStudy);
    }

    public Study findByStudyId(Long studyId){
        return studyRepository.findByStudyId(studyId);
    }

    public Map<String, List<UserStudiesResponse>> loadUserStudies(String userId){
        User user = userService.findByUserId(userId);
        List<UserStudiesResponse> availableStudyList = new ArrayList<>();
        List<UserStudiesResponse> unavailableStudyList = new ArrayList<>();

        Map<String, List<UserStudiesResponse>> studyListMap = new HashMap<>();

        // 해당 사용자가 참여하고 있는 스터디의 아이디
        List<Long> studiesId = user.getUserStudies().stream()
                .map(userStudy -> userStudy.getStudy().getStudyId())
                .collect(Collectors.toList());


        // 해당 스터디 참가자 조회
        for(Long studyId : studiesId){
            Study study = studyRepository.findByStudyId(studyId);

            // 해당 스터디에 참여하고 있는 사용자들을 조회
            List<User> users = userStudyRepository.findByStudy_StudyId(studyId).stream()
                    .map(userStudy -> userStudy.getUser())
                    .collect(Collectors.toList());

            // 추가
            if(study.getAvailable()) {
                availableStudyList.add(UserStudiesResponse.builder()
                        .studyName(study.getStudyName())
                        .studyId(studyId)
                        .participants(toListFromUsers(users))
                        .available(study.getAvailable())
                        .build());
            }
            else{
                unavailableStudyList.add(UserStudiesResponse.builder()
                        .studyName(study.getStudyName())
                        .studyId(studyId)
                        .participants(toListFromUsers(users))
                        .available(study.getAvailable())
                        .build());
            }

            studyListMap.put("available", availableStudyList);
            studyListMap.put("unavailable", unavailableStudyList);

        }

        return studyListMap;
    }

    public List<UserNameIdResponse> toListFromUsers(List<User> users){
        List<UserNameIdResponse> userNameIdResponses = new ArrayList<>();
        for(User user : users){
            userNameIdResponses.add(new UserNameIdResponse(user.getUserId(), user.getUsername()));
        }
        return userNameIdResponses;
    }
}
