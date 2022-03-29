package ch.hoc.customermanager.service;

import ch.hoc.customermanager.db.CustomerRepository;
import ch.hoc.customermanager.domain.Customer;
import ch.hoc.customermanager.dto.CustomerDTO;
import ch.hoc.customermanager.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDTO);
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDTO);
        customerRepository.delete(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return CustomerMapper.INSTANCE.customerListToCustomerDTOList(customerList);
    }

    @Override
    public CustomerDTO getCustomer(long id) {
        Customer customer = customerRepository.findEagleById(id);
        return CustomerMapper.INSTANCE.customerToCustomerDto(customer);
    }
}
