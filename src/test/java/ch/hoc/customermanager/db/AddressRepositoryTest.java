package ch.hoc.customermanager.db;

import ch.hoc.customermanager.domain.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AddressRepositoryTest {

    public static final String STREET = "Abendstrasse 30";
    public static final int PLZ = 3018;
    public static final String CITY = "Bern";

    @Autowired
    private AddressRepository addressRepo;


    @Test
    void saveAddress() {
        Address address = new Address();
        address.setStreet(STREET);
        address.setPlz(PLZ);
        address.setCity(CITY);

        addressRepo.save(address);

        List<Address> all = addressRepo.findAll();
        assertEquals(all.size(), 1);
        Address address1 = all.get(0);
        assertNotNull(address1.getId());
        assertEquals(address1.getCity(), CITY);
        assertEquals(address1.getPlz(), PLZ);
        assertEquals(address1.getStreet(), STREET);
    }
}