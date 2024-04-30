package HeartBeat.StudyConnection.service.userInfoService;

import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import HeartBeat.StudyConnection.controller.dto.userInfoDto.AddUserRequest;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .userId(dto.getUserId())
                .birth(dto.getBirth())
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getUserId();
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
