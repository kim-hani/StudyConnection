package HeartBeat.StudyConnection.service.studyArticleService;

import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import HeartBeat.StudyConnection.entity.studyArticleEntity.UserStudy;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyRepository;
import HeartBeat.StudyConnection.repository.studyArticleRepository.UserStudyRepository;
import HeartBeat.StudyConnection.service.userInfoService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserStudyRepository userStudyRepository;
    private final UserService userService;

    public Study saveStudy(String studyTitle, List<User> users, Long studyId){
        // 새로운 Study 확정
        Study study = new Study();
        Set<UserStudy> userStudies = new HashSet<>();

        study.setStudyName(studyTitle);
        study.setStudyId(studyId);

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

    public List<Study> loadUserStudies(String userId){
        User user = userService.findByUserId(userId);
        List<UserStudy> userStudyList = user.getUserStudies().stream().toList();
        List<Study> studyList = new ArrayList<>();

        for(UserStudy userStudy : userStudyList){
            studyList.add(userStudy.getStudy());
        }

        return studyList;
    }
}
