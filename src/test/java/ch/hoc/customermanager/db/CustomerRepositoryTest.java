package ch.hoc.customermanager.db;

import ch.hoc.customermanager.domain.Address;
import ch.hoc.customermanager.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerRepositoryTest {

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
    public static final String NEW_STREET = "Newstr. 00";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepo;

    @Test
    void getCustomer() throws ParseException {
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setEmail(EMAIL);
        customer.setPhoneNumber(PHONE_NUMBER);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(BIRTHDAY);
        customer.setBirthday(new Timestamp(date.getTime()));
        customer.addAddress(createAddress(STREET, PLZ));
        customer.addAddress(createAddress(STREET2, PLZ2));
        customerRepository.save(customer);

        List<Customer> all = customerRepository.findAll();
        assertEquals(all.size(), 1);
        customer = all.get(0);
        assertEquals(customer.getFirstName(), FIRST_NAME);
        assertEquals(customer.getLastName(), LAST_NAME);
        assertEquals(customer.getEmail(), EMAIL);
        assertEquals(customer.getPhoneNumber(), PHONE_NUMBER);
        customer = customerRepository.findEagleById(customer.getId());
        List<Address> addresses = customer.getAddresses();

        assertEquals(addresses.size(), 2);
        Address customerAddress = addresses.get(0);
        assertEquals(customerAddress.getCity(), CITY);
        assertEquals(customerAddress.getPlz(), PLZ);
        assertEquals(customerAddress.getStreet(), STREET);

        Address customerAddress2 = addresses.get(1);
        assertEquals(customerAddress2.getPlz(), PLZ2);
        assertEquals(customerAddress2.getStreet(), STREET2);

        customer.removeAddress(customerAddress2);
        customerAddress.setStreet(NEW_STREET);
        customerRepository.save(customer);

        customer = customerRepository.findEagleById(customer.getId());
        addresses = customer.getAddresses();

        assertEquals(addresses.size(), 1);
        customerAddress = addresses.get(0);
        assertEquals(customerAddress.getStreet(), NEW_STREET);
    }

    private Address createAddress(String street, int plz) {
        Address address = new Address();
        address.setStreet(street);
        address.setPlz(plz);
        address.setCity(CITY);
        return address;
    }
}