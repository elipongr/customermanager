package ch.hoc.customermanager.rest;

import ch.hoc.customermanager.dto.CustomerDTO;
import ch.hoc.customermanager.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") final Long id) {
        return customerService.getCustomer(id);
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void saveCustomer(@RequestBody final CustomerDTO customerDTO) {
        logger.info("was here");
        customerService.saveCustomer(customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable("id") final Long id) {
        customerService.deleteCustomer(id);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(final Exception ex) {
        final String error = "Application specific error handling";
        logger.error(error, ex);
    }
}
