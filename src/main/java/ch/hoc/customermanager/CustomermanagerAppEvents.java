package ch.hoc.customermanager;

import ch.hoc.customermanager.db.CustomerRepository;
import ch.hoc.customermanager.domain.Address;
import ch.hoc.customermanager.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CustomermanagerAppEvents {

    @Autowired
    CustomerRepository customerRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void povideDummyData() throws ParseException {
        customerRepository.saveAll(getDummyData());
    }

    private List<Customer> getDummyData() throws ParseException {
        List<Customer> customers = new ArrayList<>();
        Customer shaqiri = createCustomer("Xherdan", "Shaqiri", "shaq@attack.ch", "0792323232", "10/10/1991");
        shaqiri.getAddresses().add(createAddress("Bellvue. 3", 8012, "Basel", shaqiri));
        shaqiri.getAddresses().add(createAddress("Paradeplatz 1", 9012, "Zurich", shaqiri));
        customers.add(shaqiri);
        Customer berset = createCustomer("Alain", "Berset", "Alain@shisha.bar", "0799999999", "09/04/1972");
        berset.getAddresses().add(createAddress("Papillonallee 1", 3072, "Bern", berset));
        customers.add(berset);
        Customer hoc = createCustomer("Thai Hoc", "Dang", "hoc@customermanager.ch", "0790313027", "26/07/1993");
        hoc.getAddresses().add(createAddress("MyStr. 30", 3012, "Bern", hoc));
        customers.add(hoc);
        Customer roger = createCustomer("Roger", "Federer", "roger@wimbeldon.champ", "0711111111", "08/08/1981");
        roger.getAddresses().add(createAddress("Sesamweg. 234", 8012, "Basel", roger));
        roger.getAddresses().add(createAddress("Siegesstr. 100", 2002, "Zug", roger));
        roger.getAddresses().add(createAddress("winnnerallee. 1", 5015, "Tessin", roger));
        customers.add(roger);
        Customer schumi = createCustomer("Michael", "Schumacher", "michi@ferrarri.it", "0777777777", "03/01/1969");
        schumi.getAddresses().add(createAddress("Spitalgasse", 8000, "Basel", schumi));
        customers.add(schumi);
        return customers;
    }

    private Address createAddress(String street, int plz, String city, Customer customer) {
        Address address = new Address();
        address.setStreet(street);
        address.setPlz(plz);
        address.setCity(city);
        address.setCustomer(customer);
        return address;
    }

    private Customer createCustomer(String firstName, String lastName, String email, String phoneNumber, String bithday) throws ParseException {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(bithday);
        customer.setBirthday(new Timestamp(date.getTime()));
        return customer;
    }
}
