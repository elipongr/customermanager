package ch.hoc.customermanager.service;

import ch.hoc.customermanager.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerDTO customerDTO);

    void deleteCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomer(long id);
}
