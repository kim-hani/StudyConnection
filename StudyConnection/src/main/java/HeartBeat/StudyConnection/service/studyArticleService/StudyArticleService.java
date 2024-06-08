package HeartBeat.StudyConnection.service.studyArticleService;

import HeartBeat.StudyConnection.dto.studyArticleDto.AddStudyListResponseDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.AddStudyRequestDto;
import HeartBeat.StudyConnection.dto.studyArticleDto.UpdateStudyRequestDto;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class StudyArticleService {
    private final StudyArticleRepository studyArticleRepository;

    @Transactional
    public Long save(AddStudyRequestDto requestDto){
        return studyArticleRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, UpdateStudyRequestDto requestDto){
        StudyArticle studyArticle = studyArticleRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
        studyArticle.update(requestDto.getTitle(),requestDto.getContent());

        return id;
    }

    @Transactional
    public void setRecruitmentToFalse(StudyArticle article){
        article.setRecruitment(false);
        studyArticleRepository.save(article);
    }

    @Transactional
    public void delete(Long id){
        StudyArticle studyArticle = studyArticleRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id ="+ id));
        // 엔티티 조회 후 삭제
        studyArticleRepository.delete(studyArticle);
    }

    public StudyArticle findById(Long id) {
        StudyArticle entity = studyArticleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));

        return entity;
    }

    @Transactional(readOnly = true) //트랜젝션 범위는 유지하나, 조회 기능만 남겨서 조회 속도를 개선함
    public List<AddStudyListResponseDto> findAllDesc() {
        return studyArticleRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(AddStudyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AddStudyListResponseDto> findAllPaging(int pageNo, String criteria, String sort) {
        final int PAGE_SIZE = 10;

        Pageable pageable = (sort.equals("ASC")) ?
                PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.ASC, criteria)) :
                PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));

        Page<StudyArticle> page =  studyArticleRepository.findAll(pageable);
        Page<AddStudyListResponseDto> dtoPage = page.map(AddStudyListResponseDto::new);
        return dtoPage;
    }

    public Page<StudyArticle> getList(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.studyArticleRepository.findAll(pageable);
    }
}