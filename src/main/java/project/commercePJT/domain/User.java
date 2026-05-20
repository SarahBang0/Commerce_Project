package project.commercePJT.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "USERS")
@Getter
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
    private Level level;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
