package ch.hoc.customermanager.service;

import ch.hoc.customermanager.db.CustomerRepository;
import ch.hoc.customermanager.domain.Customer;
import ch.hoc.customermanager.dto.CustomerDTO;
import ch.hoc.customermanager.mapper.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return CustomerMapper.INSTANCE.customerListToCustomerDTOList(customerList);
    }

    @Override
    public CustomerDTO getCustomer(Long id) {
        Customer costumer = customerRepository.findCostumerById(id);
        return CustomerMapper.INSTANCE.customerToCustomerDto(costumer);
    }
}
