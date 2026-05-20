package project.commercePJT.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "address_name")
    private String name;

    private String city;
    private String street;
    private String zipcode;

    //==생성 메서드==//
    public static Address createAddress(String name, String city, String street, String zipcode) {
        Address address = new Address();
        address.name = name;
        address.city = city;
        address.street = street;
        address.zipcode = zipcode;
        return address;
    }

}
