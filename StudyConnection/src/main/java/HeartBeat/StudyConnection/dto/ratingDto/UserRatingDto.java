package HeartBeat.StudyConnection.dto.ratingDto;

import HeartBeat.StudyConnection.entity.rating.UserRating;
import HeartBeat.StudyConnection.entity.studyArticleEntity.Study;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Parameters({
//        @Parameter(name = "userId", description = "유저 id", example = "010-3507-0000"),
//        @Parameter(name = "studyId", description = "스터디 id(Long)", example = "1"),
//        @Parameter(name = "score", description = "평가점수", example = "4.4"),
//        @Parameter(name = "content", description = "평가글", example = "참 좋아요"),
//        @Parameter(name= "authorId",description = "작성자 id", example = "010-3507-0000")
//})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserRatingDto", description = "사용자 평가 정보")
public class UserRatingDto {
    @Schema(hidden = true)
    private Long id;

    @Schema(description = "사용자 ID", example = "010-3507-0000")
    private String userId;

    @Schema(hidden = true)
    private String username;

    @Schema(description = "스터디 ID", example = "1")
    private Long studyId;

    @Schema(hidden = true)
    private String studyName;

    @Schema(description = "평가 점수", example = "4.4")
    private Float score;

    @Schema(description = "평가 내용", example = "참 좋아요")
    private String content;
    //    @Schema(description = "작성자 ID", example = "010-3507-0000")
    @Schema(hidden = true)
    private String authorId;

    @Schema(hidden = true)
    private String authorName;

    @Schema(hidden = true)
    private LocalDateTime createdAt;

    @Schema(hidden = true)
    private LocalDateTime updatedAt;

    public static UserRatingDto toDto(UserRating userRating) {
        return UserRatingDto.builder()
                .id(userRating.getId())
                .userId(userRating.getUser().getUserId())
                .username(userRating.getUser().getUsername())
                .studyId(userRating.getStudy().getStudyId())
                .studyName(userRating.getStudy().getStudyName())
                .score(userRating.getScore())
                .content(userRating.getContent())
                .authorId(userRating.getAuthor().getUserId())
                .authorName(userRating.getAuthor().getUsername())
                .createdAt(userRating.getCreatedAt())
                .updatedAt(userRating.getUpdatedAt())
                .build();
    }

    public static UserRating toEntity(UserRatingDto dto, User user, Study study, User author) {
        return UserRating.builder()
                .user(user)
                .study(study)
                .score(dto.getScore())
                .content(dto.getContent())
                .author(author)
                .build();
    }
}
