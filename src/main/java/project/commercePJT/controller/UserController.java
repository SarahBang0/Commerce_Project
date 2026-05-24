package project.commercePJT.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;
import project.commercePJT.dto.UserDto;
import project.commercePJT.service.UserService;

import java.util.List;

import static project.commercePJT.dto.UserDto.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    // 회원 가입 폼 이동
    @GetMapping("/join")
    public String joinForm() {
        return "users/join";
    }

    // 회원 가입 폼
    @PostMapping
    public String join(@Valid UserJoinRequestDto dto) {
        userService.joinUser(dto);
        return "redirect:/users";
    }

    // 회원 목록
    @GetMapping
    public String users(Model model) {
        List<UserListResponseDto> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    // 회원 상세 페이지 이동
    @GetMapping("/{userId}")
    public String userDetail(@PathVariable Long userId, Model model) {
        UserDetailResponseDto user = userService.findUser(userId);
        model.addAttribute("user", user);
        return "users/detail";
    }


    // 회원 정보 수정 폼 이동
    @GetMapping("/{userId}/edit")
    public String userEditForm(@PathVariable Long userId, Model model) {
        UserDetailResponseDto user = userService.findUser(userId);
        model.addAttribute("user",user);
        return "users/edit";
    }

    // 회원 정보 수정 폼
    @PostMapping("/{userId}/edit")
    public String userEdit(@PathVariable Long userId, UserUpdateRequestDto dto) {
        userService.updateUser(userId, dto);
        return "redirect:/users/"+ userId;
    }
}
