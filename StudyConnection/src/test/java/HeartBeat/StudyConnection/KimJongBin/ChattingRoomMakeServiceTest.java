package HeartBeat.StudyConnection.KimJongBin;

import HeartBeat.StudyConnection.entity.ChatRoom;
import HeartBeat.StudyConnection.entity.User;
import HeartBeat.StudyConnection.repository.ChatRoomRepository;
import HeartBeat.StudyConnection.repository.UserRepository;
import HeartBeat.StudyConnection.service.ChattingRoomMakeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest // 테스트용 애플리케이션 컨텍스트
public class ChattingRoomMakeServiceTest {
    // 원래 컨트롤러에서 시작해 쭉 테스트하는 것 같은데
    // 내가 한 것만 먼저 테스트 하겠음.

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChattingRoomMakeService chattingRoomMakeService;

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
        List<String> receivedUserIds = new ArrayList<>();
        receivedUserIds.add("010-1234-5678");
        receivedUserIds.add("010-4321-5678");
        receivedUserIds.add("010-1234-8765");

        userRepository.save(User.builder()
                .userId("010-1234-5678")
                .birth("1999-01-05")
                .email("kkk1234@naver.com")
                .username("강건고")
                .password("0122")
                .build());
        userRepository.save(User.builder()
                .userId("010-4321-5678")
                .birth("2002-11-05")
                .email("an1234@naver.com")
                .username("김아나")
                .password("0220")
                .build());
        userRepository.save(User.builder()
                .userId("010-1234-8765")
                .birth("2003")
                .email("lle1234@naver.com")
                .username("이련이")
                .password("1234")
                .build());

        // when
        ChatRoom newChatRoom = chattingRoomMakeService.createChatRoom(roomName, receivedUserIds);

        // then
        List<ChatRoom> searchRooms = chatRoomRepository.findAll();
        System.out.println(searchRooms.size());

       assertThat(searchRooms.get(0).getId()).isEqualTo(newChatRoom.getId());


    }
}
