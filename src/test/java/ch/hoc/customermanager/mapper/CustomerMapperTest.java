package ch.hoc.customermanager.mapper;

import ch.hoc.customermanager.domain.Address;
import ch.hoc.customermanager.domain.Customer;
import ch.hoc.customermanager.dto.AddressDTO;
import ch.hoc.customermanager.dto.CustomerDTO;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {
    public static final String FIRST_NAME = "Thai Hoc";
    public static final String LAST_NAME = "Dang";
    public static final String EMAIL = "thaihoc.dang@hotmail.com";
    public static final String PHONE_NUMBER = "0799421526";
    public static final String BIRTHDAY = "26/07/1993";
    public static final String STREET = "Abendstrasse 30";
    public static final int PLZ = 3018;
    public static final String CITY = "Bern";
    public static final String STREET2 = "Melchiorstrasse 13";
    public static final int PLZ2 = 3027;

    @Test
    void customerToCustomerDto() throws ParseException {
        Customer customer = createCustomer();
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDto(customer);
        verifyCustumerDTO(customerDTO);
    }


    @Test
    void customerDtoToCustomer() throws ParseException {
        CustomerDTO customerDTO = createCustomerDTO();
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDTO);
        verifyCustomer(customer);
    }

    @Test
    void customerListToCustomerDtoList() throws ParseException {
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(createCustomer());
        customerList.add(createCustomer());
        List<CustomerDTO> customerDTOList = CustomerMapper.INSTANCE.customerListToCustomerDTOList(customerList);
        assertEquals(customerDTOList.size(), 2);
        verifyCustumerDTO(customerDTOList.get(0));
        verifyCustumerDTO(customerDTOList.get(1));
    }

    @Test
    void customerDTOListToCustomerList() throws ParseException {
        ArrayList<CustomerDTO> customerDTOList = new ArrayList<>();
        customerDTOList.add(createCustomerDTO());
        customerDTOList.add(createCustomerDTO());
        List<Customer> customerList = CustomerMapper.INSTANCE.customerDTOListToCustomerList(customerDTOList);
        assertEquals(customerList.size(), 2);
        verifyCustomer(customerList.get(0));
        verifyCustomer(customerList.get(1));
    }

    private void verifyCustumerDTO(CustomerDTO customerDTO) {
        assertEquals(customerDTO.getFirstName(), FIRST_NAME);
        assertEquals(customerDTO.getLastName(), LAST_NAME);
        assertEquals(customerDTO.getEmail(), EMAIL);
        assertEquals(customerDTO.getPhoneNumber(), PHONE_NUMBER);
        List<AddressDTO> addressDTOs = customerDTO.getAddresses();

        assertEquals(addressDTOs.size(), 2);
        AddressDTO addressDTO = addressDTOs.get(0);
        assertEquals(addressDTO.getCity(), CITY);
        assertEquals(addressDTO.getPlz(), PLZ);
        assertEquals(addressDTO.getStreet(), STREET);

        AddressDTO addressDTO2 = addressDTOs.get(1);
        assertEquals(addressDTO2.getPlz(), PLZ2);
        assertEquals(addressDTO2.getStreet(), STREET2);
    }

    private void verifyCustomer(Customer customer) {
        assertEquals(customer.getFirstName(), FIRST_NAME);
        assertEquals(customer.getLastName(), LAST_NAME);
        assertEquals(customer.getEmail(), EMAIL);
        assertEquals(customer.getPhoneNumber(), PHONE_NUMBER);
        List<Address> addresses = customer.getAddresses();

        assertEquals(addresses.size(), 2);
        Address address = addresses.get(0);
        assertEquals(address.getCity(), CITY);
        assertEquals(address.getPlz(), PLZ);
        assertEquals(address.getStreet(), STREET);
        Customer addressCustomer = address.getCustomer();
        assertEquals(addressCustomer.getFirstName(), FIRST_NAME);
        assertEquals(addressCustomer.getLastName(), LAST_NAME);
        assertEquals(addressCustomer.getEmail(), EMAIL);
        assertEquals(addressCustomer.getPhoneNumber(), PHONE_NUMBER);

        Address address2 = addresses.get(1);
        assertEquals(address2.getPlz(), PLZ2);
        assertEquals(address2.getStreet(), STREET2);
        Customer addressCustomer2 = address.getCustomer();
        assertEquals(addressCustomer2.getFirstName(), FIRST_NAME);
        assertEquals(addressCustomer2.getLastName(), LAST_NAME);
        assertEquals(addressCustomer2.getEmail(), EMAIL);
        assertEquals(addressCustomer2.getPhoneNumber(), PHONE_NUMBER);
    }

    private Address createAddress(String street, int plz) {
        Address address = new Address();
        address.setStreet(street);
        address.setPlz(plz);
        address.setCity(CITY);
        return address;
    }

    private Customer createCustomer() throws ParseException {
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setEmail(EMAIL);
        customer.setPhoneNumber(PHONE_NUMBER);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(BIRTHDAY);
        customer.setBirthday(new Timestamp(date.getTime()));
        customer.getAddresses().add(createAddress(STREET, PLZ));
        customer.getAddresses().add(createAddress(STREET2, PLZ2));
        return customer;
    }

    private AddressDTO createAddressDTO(String street, int plz) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(street);
        addressDTO.setPlz(plz);
        addressDTO.setCity(CITY);
        return addressDTO;
    }

    private CustomerDTO createCustomerDTO() throws ParseException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setEmail(EMAIL);
        customerDTO.setPhoneNumber(PHONE_NUMBER);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(BIRTHDAY);
        customerDTO.setBirthday(new Timestamp(date.getTime()));
        customerDTO.getAddresses().add(createAddressDTO(STREET, PLZ));
        customerDTO.getAddresses().add(createAddressDTO(STREET2, PLZ2));
        return customerDTO;
    }
}