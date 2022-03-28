package ch.hoc.customermanager.service;

import ch.hoc.customermanager.db.AddressRepository;
import ch.hoc.customermanager.db.CustomerRepository;
import ch.hoc.customermanager.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

}
