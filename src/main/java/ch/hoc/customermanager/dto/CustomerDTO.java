package ch.hoc.customermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@FieldNameConstants
@Setter
@Getter
public class CustomerDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Timestamp birthday;

    private List<AddressDTO> addresses = new ArrayList<>();
}
