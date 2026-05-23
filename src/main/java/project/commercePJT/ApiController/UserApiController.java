package project.commercePJT.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.commercePJT.service.UserService;

import java.util.List;

import static project.commercePJT.dto.UserDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private final UserService userService;

    // 회원 상세 조회
    @GetMapping("/{userId}")
    public UserDetailResponseDto user(@PathVariable Long userId) {
        return userService.findUser(userId);
    }

    // 회원 목록 조회
    @GetMapping
    public List<UserListResponseDto> users() {
        return userService.findUsers();
    }

    // 회원 가입
    @PostMapping
    public Long join(@RequestBody UserJoinRequestDto dto) {
        return userService.joinUser(dto);
    }

    // 회원 정보 수정
    @PostMapping("/{userId}")
    public UserDetailResponseDto updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto dto) {
        userService.updateUser(userId, dto);
        return userService.findUser(userId);
    }
}
