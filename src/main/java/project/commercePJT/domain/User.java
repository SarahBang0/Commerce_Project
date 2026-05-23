package project.commercePJT.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    private String email;
    private String password;
    private LocalDateTime joined_date;

    @Enumerated (EnumType.STRING)
    private Level level = Level.BRONZE;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    //==생성 메서드==//
    public static User createUser(String name, String email,String password, Address address) {
        User user = new User();
        user.name = name;
        user.email = email;
        user.password = password;
        user.level = Level.BRONZE;
        user.joined_date = LocalDateTime.now();
        user.address = address;
        return user;
    }

    //==수정 메서드==//
    public void changeProfile(String name, Address address) {
        this.name = name;
        this.address = address;
    }


}
