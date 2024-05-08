package HeartBeat.StudyConnection.service.userInfoService;

import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import HeartBeat.StudyConnection.dto.signUpDto.AddUserRequest;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .userId(dto.getUserId())
                .birth(dto.getBirth())
                .username(dto.getUserName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build());
    }

    public User findById(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        } else {
            // 사용자가 존재하지 않을 때 처리
            return null;
        }
    }
}
