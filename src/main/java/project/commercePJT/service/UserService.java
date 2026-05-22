package project.commercePJT.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.User;
import project.commercePJT.dto.UserDto;
import project.commercePJT.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static project.commercePJT.dto.UserDto.*;

@Service
@Getter
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 등록
    @Transactional
    public Long joinUser(UserJoinRequestDto dto) {
        validateDuplicateUser(dto.getEmail());
        User user = User.createUser(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getAddress());
        return userRepository.save(user).getId();
    }

    // 회원 정보 수정
    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto dto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalStateException("해당 회원이 존재하지 않습니다. Id=" + userId));
        findUser.changeProfile(dto.getName(),dto.getAddress());
    }

    // 회원 목록 조회
    public List<UserResponseDto> findUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();
    }

    // 회원 상세 조회
    public UserResponseDto findUser(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalStateException("해당 회원이 존재하지 않습니다. Id="+ userId));
        return new UserResponseDto(findUser);
    }

    private void validateDuplicateUser(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }
    }
}
