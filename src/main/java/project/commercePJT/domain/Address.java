package project.commercePJT.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

    @Column(name = "address_name")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
