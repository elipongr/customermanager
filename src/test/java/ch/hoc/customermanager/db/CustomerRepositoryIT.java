package ch.hoc.customermanager.db;

import ch.hoc.customermanager.domain.Address;
import ch.hoc.customermanager.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CustomerRepositoryIT {

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
    public static final String NEW_EMAIL = "newmail@mail.new";

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void saveCustomer() throws ParseException {
        int customerCount = customerRepository.findAll().size();
        Customer customer = customerRepository.save(createCustomer());

        assertEquals(customerRepository.findAll().size(), customerCount + 1);
        customer = customerRepository.findCostumerById(customer.getId());
        assertEquals(customer.getFirstName(), FIRST_NAME);
        assertEquals(customer.getLastName(), LAST_NAME);
        assertEquals(customer.getEmail(), EMAIL);
        assertEquals(customer.getPhoneNumber(), PHONE_NUMBER);
    }

    @Test
    void saveCustomerWithAddress() throws ParseException {
        int customerCount = customerRepository.findAll().size();
        Customer customer = createCustomer();
        customer.getAddresses().add(createAddress(STREET, PLZ, customer));
        customer.getAddresses().add(createAddress(STREET2, PLZ2, customer));
        customer = customerRepository.save(customer);

        assertEquals(customerRepository.findAll().size(), customerCount + 1);
        customer = customerRepository.findCostumerById(customer.getId());
        List<Address> addresses = customer.getAddresses();

        assertEquals(addresses.size(), 2);
        Address customerAddress = addresses.get(0);
        assertEquals(customerAddress.getCity(), CITY);
        assertEquals(customerAddress.getPlz(), PLZ);
        assertEquals(customerAddress.getStreet(), STREET);

        Address customerAddress2 = addresses.get(1);
        assertEquals(customerAddress2.getPlz(), PLZ2);
        assertEquals(customerAddress2.getStreet(), STREET2);
    }

    @Test
    void updateAddresses() throws ParseException {
        int customerCount = customerRepository.findAll().size();
        Customer customer = createCustomer();
        customer.getAddresses().add(createAddress(STREET, PLZ, customer));
        customer.getAddresses().add(createAddress(STREET2, PLZ2, customer));
        customer = customerRepository.save(customer);

        assertEquals(customerRepository.findAll().size(), customerCount + 1);
        customer = customerRepository.findCostumerById(customer.getId());
        List<Address> addresses = customer.getAddresses();

        assertEquals(addresses.size(), 2);
        Address customerAddress = addresses.get(0);
        Address customerAddress2 = addresses.get(1);
        customer.getAddresses().remove(customerAddress2);
        customerAddress.setStreet(NEW_STREET);
        customerRepository.save(customer);

        customer = customerRepository.findCostumerById(customer.getId());
        addresses = customer.getAddresses();

        assertEquals(addresses.size(), 1);
        customerAddress = addresses.get(0);
        assertEquals(customerAddress.getStreet(), NEW_STREET);
    }

    @Test
    void updateCustomer() throws ParseException {
        int customerCount = customerRepository.findAll().size();
        Customer customer = createCustomer();
        customer.getAddresses().add(createAddress(STREET, PLZ, customer));
        customer.getAddresses().add(createAddress(STREET2, PLZ2, customer));
        customer = customerRepository.save(customer);

        assertEquals(customerRepository.findAll().size(), customerCount + 1);
        customer = customerRepository.findCostumerById(customer.getId());
        List<Address> addresses = customer.getAddresses();
        assertEquals(addresses.size(), 2);

        Customer newCustomer = createCustomer();
        newCustomer.setEmail(NEW_EMAIL);
        newCustomer.setId(customer.getId());
        customerRepository.save(newCustomer);

        customer = customerRepository.findCostumerById(customer.getId());
        assertEquals(customer.getEmail(), NEW_EMAIL);
        addresses = customer.getAddresses();
        assertEquals(addresses.size(), 0);
    }

    @Test
    void deleteCustomer() throws ParseException {
        int customerCount = customerRepository.findAll().size();
        Customer customer = createCustomer();
        customer.getAddresses().add(createAddress(STREET, PLZ, customer));
        customer.getAddresses().add(createAddress(STREET2, PLZ2, customer));
        customer = customerRepository.save(customer);

        assertEquals(customerRepository.findAll().size(), customerCount + 1);
        customerRepository.deleteById(customer.getId());
        assertEquals(customerRepository.findAll().size(), customerCount);
    }

    private Address createAddress(String street, int plz, Customer customer) {
        Address address = new Address();
        address.setStreet(street);
        address.setPlz(plz);
        address.setCity(CITY);
        address.setCustomer(customer);
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
        return customer;
    }
}