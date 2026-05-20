package project.commercePJT.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.Address;
import project.commercePJT.domain.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    void 회원_생성_성공() {
        //given
        Address address = getAddress();
        User user = getUser(address);

        //when
        userRepository.save(user);

        //then
        User findUser = userRepository.findByName(user.getName()).get(0);
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(userRepository.findByName(user.getName()).size()).isEqualTo(1);

    }

    @Test
    void 회원_삭제_성공() {
        //given
        Address address = getAddress();
        User user = getUser(address);
        userRepository.save(user);

        //when
        userRepository.delete(user);

        //then
        assertThat(userRepository.findByName(user.getName()).size()).isEqualTo(0);
    }

    @Test
    void 회원_정보_수정_성공() {
        //given
        Address address = getAddress();
        User user = getUser(address);
        userRepository.save(user);

        //when
        user.changeProfile("Spring", address);

        //then
        User findUser = userRepository.findByName(user.getName()).get(0);
        assertThat(user.getName()).isEqualTo("Spring");
        assertThat(findUser).isEqualTo(user);
    }



    private static User getUser(Address address) {
        User user = User.createUser("Java", "java@gmail.com", "1234", address);
        return user;
    }

    private static Address getAddress() {
        Address address = Address.createAddress("home", "seoul", "gangnam", "54321");
        return address;
    }

}