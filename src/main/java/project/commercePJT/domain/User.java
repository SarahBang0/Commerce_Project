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

    private Long totalOrderPrice;

    //==생성 메서드==//
    public static User createUser(String name, String email,String password, Address address) {
        User user = new User();
        user.name = name;
        user.email = email;
        user.password = password;
        user.level = Level.BRONZE;
        user.joined_date = LocalDateTime.now();
        user.address = address;
        user.totalOrderPrice = 0L;
        return user;
    }

    //==수정 메서드==//
    public void changeProfile(String name, Address address) {
        if(name != null) {
            this.name = name;
        }
        if(address.getCity()!= null) {
            this.address.getCity() = address.getCity();
        }
    }

    public void addTotalOrderPrice(Long price) {
        this.totalOrderPrice += price;
        updateGrade();
    }

    public void removeTotalOrderPrice(Long price) {
        this.totalOrderPrice -= price;
        updateGrade();
    }

    //==등급 업데이트 로직==//
    private void updateGrade() {
        if(this.totalOrderPrice >= 150000) {
            this.level = Level.GOLD;
        } else if(this.totalOrderPrice >= 70000) {
            this.level = Level.SLIVER;
        }
    }
}
