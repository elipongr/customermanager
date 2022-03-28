package ch.hoc.customermanager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@FieldNameConstants
@Entity
@Setter
@Getter
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Timestamp birthday;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public void addAddress(Address address) {
        addresses.add(address);
        address.setCustomer(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setCustomer(null);
    }
}
