package HeartBeat.StudyConnection.service.userInfoService;

import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import HeartBeat.StudyConnection.dto.signUpDto.AddUserRequest;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .userId(dto.getUserId())
                .birth(dto.getBirth())
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build());
    }

    public User findByUserId(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        } else {
            // 사용자가 존재하지 않을 때 처리
            System.out.println("없어요!!!!!!!!!!");
            return null;
        }
    }
}
