package HeartBeat.StudyConnection.KimJongBin;

import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoom;
import HeartBeat.StudyConnection.entity.chatRoomEntity.ChatRoomAndUser;
import HeartBeat.StudyConnection.entity.userInfoEntity.User;
import HeartBeat.StudyConnection.repository.chatRoomRepository.ChatRoomAndUserRepository;
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
    @Autowired
    ChatRoomAndUserRepository chatRoomAndUserRepository;

    // Service
    @Autowired
    ChattingRoomService chattingRoomMakeService;

    @BeforeEach
    void cleanRepository(){
        userRepository.deleteAll();
        chatRoomRepository.deleteAll();
        chatRoomAndUserRepository.deleteAll();
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

        // when
        ChatRoom newChatRoom = chattingRoomMakeService.createChatRoom(roomName, receivedUser, Long.valueOf(1));

        // then (결과)
        List<ChatRoom> searchRooms = chatRoomRepository.findAll();
        List<ChatRoomAndUser> chatRoomAndUsers = chatRoomAndUserRepository.findAll();
        List<User> users = userRepository.findAll();

        System.out.println(searchRooms.size());
        System.out.println(chatRoomAndUsers.size());

        // 새로 만든 톡방 잘 저장되었는지
       assertThat(searchRooms.get(0).getId()).isEqualTo(newChatRoom.getId());

       // 유저 톡방에 잘 저장되었는지
        List<String> chatRoomAndUserSide = new ArrayList<>();
        chatRoomAndUserSide.add(chatRoomAndUsers.get(0).getUser().getUserId());
        chatRoomAndUserSide.add(chatRoomAndUsers.get(1).getUser().getUserId());
        chatRoomAndUserSide.add(chatRoomAndUsers.get(2).getUser().getUserId());

        List<String> userSide = new ArrayList<>();
        userSide.add(users.get(0).getUserId());
        userSide.add(users.get(1).getUserId());
        userSide.add(users.get(2).getUserId());

        assertThat(chatRoomAndUserSide).containsExactlyInAnyOrderElementsOf(userSide);

        // 새로 만든 톡방과 톡방-유저 테이블이 맞는지
        assertThat(chatRoomAndUsers.get(0).getChatRoom().getId())
                .isEqualTo(newChatRoom.getId());

        assertThat(chatRoomAndUsers.get(1).getChatRoom().getId())
                .isEqualTo(newChatRoom.getId());

        assertThat(chatRoomAndUsers.get(2).getChatRoom().getId())
                .isEqualTo(newChatRoom.getId());


    }
}
