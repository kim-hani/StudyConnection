package HeartBeat.StudyConnection.dto.UserWithStudyDto;

import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNameIdResponse {
    private String userId;
    private String username;
}
