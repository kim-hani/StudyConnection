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
    private final CommentRepository commentRepositroy;

    @Transactional(readOnly = true)
    public List<SummarizedCommentDto> list(Long studyArticleId){
        List<Comment> comments = commentRepositroy.findByStudyArticleIdExceptReply(studyArticleId);
        return comments.stream().map(e->{
            List<Comment> replies = commentRepositroy.findAllReplies(e.getId());
            return new SummarizedCommentDto(e, replies.stream().map(SummarizedReplyDto::new).toList());
        }).collect(Collectors.toList());
    }

    @Transactional
    public Long create(int studyArticleId,String userId,String content){
        StudyArticle studyArticle = studyArticleRepository.findById(studyArticleId).orElseThrow(StudyArticleNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Comment comment = Comment.builder()
                .user(user)
                .studyArticle(studyArticle)
                .content(content)
                .commentStatus(CommentStatus.ACTIVE)
                .build();

        comment.changeStudyArticle(studyArticle);
        comment = commentRepositroy.save(comment);
        return comment.getId();
    }

    @Transactional
    public void edit(Long commentId,String userId,String content){
        Comment comment = commentRepositroy.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!comment.getUser().getUserId().equals(userId)){
            throw new   NotGrantedException();
        }
        if(comment.getCommentStatus() == CommentStatus.ACTIVE
                || comment.getCommentStatus() == CommentStatus.EDITED) {
            comment.updateContent(content);
        }
    }

    @Transactional
    public void delete(Long commentId, String userId) {
        Comment comment = commentRepositroy.findById(commentId).orElseThrow(CommentNotFoundException::new);



    }

}
