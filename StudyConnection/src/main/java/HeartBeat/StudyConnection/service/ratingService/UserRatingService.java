package HeartBeat.StudyConnection.service.ratingService;

import HeartBeat.StudyConnection.dto.ratingDto.UserRatingDto;
import HeartBeat.StudyConnection.entity.rating.UserRating;
import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.ratingRepository.UserRatingRepository;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyRepository;
import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRatingService {

    private final UserRatingRepository userRatingRepository;
    private final UserRepository userRepository;
    private final StudyRepository studyRepository;

    public UserRating toEntity(UserRatingDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + dto.getUserId()));
        Study study = studyRepository.findById(dto.getStudyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid study ID: " + dto.getStudyId()));
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid author ID: " + dto.getAuthorId()));

        return UserRatingDto.toEntity(dto, user, study, author);
    }

    public Long save(UserRatingDto userRatingDto){
        UserRating userRating = toEntity(userRatingDto);
        return userRatingRepository.save(userRating).getId();
    }

    public UserRatingDto findById(Long id){
        UserRating userRating = userRatingRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 평가가 없습니다. id ="+id));
        return UserRatingDto.toDto(userRating);
    }

    public List<UserRatingDto> findAll(){
        List<UserRating> userRatings = userRatingRepository.findAll();
        List<UserRatingDto> userRatingDtos = new ArrayList<>();
        for(UserRating userRating : userRatings){
            userRatingDtos.add(UserRatingDto.toDto(userRating));
        }
        return userRatingDtos;
    }

    public List<UserRatingDto> findByUserId(String userId){
        List<UserRating> userRatings = userRatingRepository.findByUser_userId(userId);
        List<UserRatingDto> userRatingDtos = new ArrayList<>();
        for(UserRating userRating : userRatings){
            userRatingDtos.add(UserRatingDto.toDto(userRating));
        }
        return userRatingDtos;
    }

    // 평가점수, 평가글 수정
    public Long update(UserRatingDto userRatingDto){
        UserRating userRating = userRatingRepository.findById(userRatingDto.getId()).orElseThrow(()->new IllegalArgumentException("해당 평가가 없습니다. id ="+userRatingDto.getId()));
        userRating.setScore(userRatingDto.getScore());
        userRating.setContent(userRatingDto.getContent());
        return userRatingRepository.save(userRating).getId();
    }

    public void deleteById(Long id){
        UserRating userRating = userRatingRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 평가가 없습니다. id ="+id));
        userRatingRepository.delete(userRating);
    }


}
