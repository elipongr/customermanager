package ch.hoc.customermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Setter
@Getter
public class AddressDTO {

    private Long id;

    private String street;

    private int plz;

    private String city;

    private CustomerDTO customer;
}
