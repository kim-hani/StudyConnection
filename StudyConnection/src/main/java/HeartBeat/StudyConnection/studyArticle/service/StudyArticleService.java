package HeartBeat.StudyConnection.studyArticle.service;

import HeartBeat.StudyConnection.studyArticle.dto.AddStudyListResponseDto;
import HeartBeat.StudyConnection.studyArticle.dto.AddStudyRequestDto;
import HeartBeat.StudyConnection.studyArticle.dto.StudyResponseDto;
import HeartBeat.StudyConnection.studyArticle.dto.UpdateStudyRequestDto;
import HeartBeat.StudyConnection.studyArticle.entity.StudyArticle;
import HeartBeat.StudyConnection.studyArticle.repository.StudyArticleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyArticleService {
    private final StudyArticleRepository studyArticleRepository;

    @Transactional
    public int save(AddStudyRequestDto requestDto){
        return studyArticleRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public int update(int id, UpdateStudyRequestDto requestDto){
        StudyArticle studyArticle = studyArticleRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
        studyArticle.update(requestDto.getTitle(),requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(int id){
        StudyArticle studyArticle = studyArticleRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id ="+ id));
        // 엔티티 조회 후 삭제
        studyArticleRepository.delete(studyArticle);
    }

    public StudyResponseDto findById(int id) {
        StudyArticle entity = studyArticleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));

        return new StudyResponseDto(entity);
    }

    @Transactional(readOnly = true) //트랜젝션 범위는 유지하나, 조회 기능만 남겨서 조회 속도를 개선함
    public List<AddStudyListResponseDto> findAllDesc() {
        return studyArticleRepository.findAllDesc().stream()
                .map(studyArticle -> new AddStudyListResponseDto(studyArticle))
                .collect(Collectors.toList());
}