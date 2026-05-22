package project.commercePJT.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.Address;
import project.commercePJT.dto.UserDto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static project.commercePJT.dto.UserDto.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired
    EntityManager em;

    @Test
    void 회원_등록_성공() {
        //given
        UserJoinRequestDto userJoinRequestDto = getUserRequestDto();
        //when
        Long userId = userService.joinUser(userJoinRequestDto);
        //then
        UserResponseDto findUser = userService.findUser(userId);
        assertThat(findUser.getName()).isEqualTo(userJoinRequestDto.getName());

    }

    @Test
    void 회원_이메일_중복() {
        //given
        UserJoinRequestDto userJoinRequestDto1 = getUserRequestDto();
        Long userId = userService.joinUser(userJoinRequestDto1);

        //when
        UserJoinRequestDto userJoinRequestDto2 = new UserJoinRequestDto("Java", "java@gmail.com", "12345", getAddress());

        //then
        assertThrows(IllegalStateException.class,
                ()-> userService.joinUser(userJoinRequestDto2));
    }

    @Test
    void 회원_정보_수정_성공() {
        //when
        UserJoinRequestDto userJoinRequestDto = getUserRequestDto();
        Long userId = userService.joinUser(userJoinRequestDto);

        //then
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("new Spring", getAddress());
        userService.updateUser(userId, userUpdateRequestDto);

        em.flush();
        em.clear();

        //then
        UserResponseDto findUser = userService.findUser(userId);
        assertThat(findUser.getName()).isEqualTo("new Spring");
    }

    private static Address getAddress() {
        return Address.createAddress("home", "seoul", "gangnam", "54321");
    }

    private static UserJoinRequestDto getUserRequestDto() {
        Address address = getAddress();
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto("Spring", "java@gmail.com", "12345", address);
        return userJoinRequestDto;
    }
}
