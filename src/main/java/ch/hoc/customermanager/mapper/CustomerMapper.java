package ch.hoc.customermanager.mapper;

import ch.hoc.customermanager.domain.Address;
import ch.hoc.customermanager.domain.Customer;
import ch.hoc.customermanager.dto.CustomerDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public abstract class CustomerMapper {
    public static CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public abstract CustomerDTO customerToCustomerDto(Customer customer);

    public abstract Customer customerDtoToCustomer(CustomerDTO customerDTO);

    public abstract List<Customer> customerDTOListToCustomerList(List<CustomerDTO> customerDTOList);

    public abstract List<CustomerDTO> customerListToCustomerDTOList(List<Customer> customerList);

    @AfterMapping
    public void afterMappingCustomerDtoToCustomer(@MappingTarget Customer customer) {
        List<Address> addresses = customer.getAddresses();
        if (addresses != null) {
            addresses.forEach(a -> a.setCustomer(customer));
        }
    }
}
