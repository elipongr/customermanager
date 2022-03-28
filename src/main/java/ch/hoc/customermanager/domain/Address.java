package ch.hoc.customermanager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@FieldNameConstants
@Entity
@Setter
@Getter
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String street;

    private int plz;

    private String city;

    @ManyToOne
    private Customer customer;
}
