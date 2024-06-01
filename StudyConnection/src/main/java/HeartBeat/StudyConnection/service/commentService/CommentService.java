package HeartBeat.StudyConnection.service.commentService;


import HeartBeat.StudyConnection.CommentStatus;
import HeartBeat.StudyConnection.dto.commentDto.response.SummarizedCommentDto;
import HeartBeat.StudyConnection.dto.commentDto.response.SummarizedReplyDto;
import HeartBeat.StudyConnection.entity.commentEntity.Comment;
import HeartBeat.StudyConnection.exception.CommentNotFoundException;
import HeartBeat.StudyConnection.exception.NotGrantedException;
import HeartBeat.StudyConnection.exception.StudyArticleNotFoundException;
import HeartBeat.StudyConnection.exception.UserNotFoundException;
import HeartBeat.StudyConnection.repository.commentRepository.CommentRepository;
import HeartBeat.StudyConnection.entity.studyArticleEntity.StudyArticle;
import HeartBeat.StudyConnection.repository.studyArticleRepository.StudyArticleRepository;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final StudyArticleRepository studyArticleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // 데이터베이스 내용 조회
    // 댓글 조회하고 리스트로 반환
    @Transactional(readOnly = true)
    public List<SummarizedCommentDto> list(Long studyArticleId){
        List<Comment> comments = commentRepository.findByStudyArticleIdExceptReply(studyArticleId);
        return comments.stream().map(e->{
            List<Comment> replies = commentRepository.findAllReplies(e.getId());
            return new SummarizedCommentDto(e, replies.stream().map(SummarizedReplyDto::new).toList());
        }).collect(Collectors.toList());
    }

    // 해당 게시물에 댓글 생성
    @Transactional
    public Long create(Long studyArticleId,String userId,String content){
        StudyArticle studyArticle = studyArticleRepository.findById(studyArticleId).orElseThrow(StudyArticleNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Comment comment = Comment.builder()
                .user(user)
                .studyArticle(studyArticle)
                .content(content)
                .commentStatus(CommentStatus.ACTIVE)
                .build();

        comment.changeStudyArticle(studyArticle);
        comment = commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void edit(Long commentId,String userId,String content){
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!comment.getUser().getUserId().equals(userId)){
            throw new NotGrantedException();
        }
        if(comment.getCommentStatus() == CommentStatus.ACTIVE
                || comment.getCommentStatus() == CommentStatus.EDITED) {
            comment.updateContent(content);
        }
    }

    @Transactional
    public void delete(Long commentId, String userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new NotGrantedException();
        }

        if (comment.getCommentStatus() == CommentStatus.ACTIVE || comment.getCommentStatus() == CommentStatus.EDITED) {
            comment.markedAsDeleted(false); // 일반 사용자에 의해 삭제됨
        }

    }

    // 대댓글 생성
    // 부모댓글 Id로부터 댓글 조회
    public Long createReply(Long commentId, String userId, String content) {
        Comment parentComment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        Long studyArticleId = parentComment.getStudyArticle().getId();
        StudyArticle studyArticle = studyArticleRepository.findById(studyArticleId).orElseThrow(StudyArticleNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Comment comment = Comment.builder()
                .user(user)
                .studyArticle(studyArticle)
                .content(content)
                .commentStatus(CommentStatus.ACTIVE)
                .parentCommentId(parentComment.getId())
                .build();

        comment.changeStudyArticle(studyArticle);
        comment = commentRepository.save(comment);
        parentComment.addChildComment(comment);
        return comment.getId();
    }
}
