package ch.hoc.customermanager.mapper;

import ch.hoc.customermanager.domain.Customer;
import ch.hoc.customermanager.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDTO customer);

    List<Customer> customerDTOListToCustomerList(List<CustomerDTO> customerDTOList);

    List<CustomerDTO> customerListToCustomerDTOList(List<Customer> customerList);
}
