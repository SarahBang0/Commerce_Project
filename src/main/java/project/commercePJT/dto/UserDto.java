package project.commercePJT.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.Address;
import project.commercePJT.domain.Level;
import project.commercePJT.domain.Order;
import project.commercePJT.domain.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class UserDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UserJoinRequestDto {
        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String name;

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 5, message = "비밀번호는 5글자 이상이어야 합니다.")
        private String password;

        @NotNull(message = "주소는 필수 입력값입니다.")
        private Address address ;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UserUpdateRequestDto {
        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String name;

        @NotNull(message = "주소는 필수 입력값입니다.")
        private Address address;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserDetailResponseDto {

        private Long userId;
        private String name;
        private String email;
        private String joinedDate;
        private Address address;
        private String zipcode;
        private Level level;
        private Long totalOrderPrice;
        private List<OrderDto.OrderResponseDto> orders = new ArrayList<>();

        public UserDetailResponseDto(User user) {
            this.userId = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.joinedDate = user.getJoined_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.address = user.getAddress();
            this.zipcode = user.getAddress().getZipcode();
            this.level = user.getLevel();
            this.totalOrderPrice = user.getTotalOrderPrice();
            for (Order order : user.getOrders()) {
                this.orders.add(new OrderDto.OrderResponseDto(order));
            }
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserListResponseDto {

        private Long id;
        private String name;
        private String email;
        private Long totalOrderPrice;
        private Level level;

        public UserListResponseDto(User user) {
            this.name = user.getName();
            this.email = user.getEmail();
            this.totalOrderPrice = user.getTotalOrderPrice();
            this.level = user.getLevel();
            this.id = user.getId();
        }
    }

}
