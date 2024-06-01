package HeartBeat.StudyConnection.KimJongBin;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoom;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.chatRoomRepository.ChatRoomRepository;
import HeartBeat.StudyConnection.repository.userInfoRepository.UserRepository;
import HeartBeat.StudyConnection.service.chatRoomService.ChattingRoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest // 테스트용 애플리케이션 컨텍스트
public class ChattingRoomMakeServiceTest {
    // 원래 컨트롤러에서 시작해 쭉 테스트하는 것 같은데
    // 내가 한 것만 먼저 테스트 하겠음.

    // Repository
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;

    // Service
    @Autowired
    ChattingRoomService chattingRoomMakeService;

    @BeforeEach
    void cleanRepository(){
        userRepository.deleteAll();
        chatRoomRepository.deleteAll();
    }

    @DisplayName("1+3=4")
    @Test
    public void junitTest(){
        int a = 1;
        int b = 3;
        int sum = 4;
        Assertions.assertEquals(sum, a+b);
    }

    @DisplayName("스터디 확정 시 채팅방 만드는 기능")
    @Test
    public void ServiceTest(){
        // given
        String roomName = "테스트 채팅방";

        // 유저 저장
        List<User> receivedUser = new ArrayList<>();
        receivedUser.add(userRepository.save(User.builder()
                .userId("010-1234-5678")
                .birth("1999-01-05")
                .email("kkk1234@naver.com")
                .username("강건고")
                .password("0122")
                .build()));

        receivedUser.add(userRepository.save(User.builder()
                .userId("010-4321-5678")
                .birth("2002-11-05")
                .email("an1234@naver.com")
                .username("김아나")
                .password("0220")
                .build()));

        receivedUser.add(userRepository.save(User.builder()
                .userId("010-1234-8765")
                .birth("2003")
                .email("lle1234@naver.com")
                .username("이련이")
                .password("1234")
                .build()));

        List<String> userIdToCheck = List.of("010-1234-5678", "010-4321-5678", "010-1234-8765");

        // when
        chattingRoomMakeService.createChatRoom(roomName, receivedUser, Long.valueOf(1));

        // then (결과)
        List<ChatRoom> searchRooms = chatRoomRepository.findAll();
        List<ChatRoom> searchRoomsByStudyId = chatRoomRepository.findByStudyId(Long.valueOf(1));
        List<ChatRoom> searchRoomsByUserId = chatRoomRepository.findByUserId("010-1234-8765");
        List<User> users = userRepository.findAll();

        List<String> userIdsInChatRooms = searchRooms.stream()
                .map(ChatRoom::getUserId)
                .collect(Collectors.toList());

        // 새로 만든 톡방 잘 저장되었는지
       assertThat(searchRooms.size()).isEqualTo(3);

        // 새로 만든 톡방과 톡방-유저 테이블이 맞는지
        assertThat(searchRoomsByUserId.get(0).getRoomName())
                .isEqualTo(roomName);

        assertThat(searchRoomsByUserId.get(0).getUserId())
                .isEqualTo("010-1234-8765");

        assertThat(searchRoomsByStudyId.get(0).getStudyId())
                .isEqualTo(Long.valueOf(1));

        assertThat(userIdsInChatRooms).containsAll(userIdToCheck);


    }
}
