package HeartBeat.StudyConnection.controller.dto.commentDto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateCommentDto {

    @NotBlank
    private String content;

    public RequestCreateCommentDto(String content) {
        this.content = content;
    }
}
