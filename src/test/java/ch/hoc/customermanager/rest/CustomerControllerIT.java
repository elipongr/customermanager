package ch.hoc.customermanager.rest;

import ch.hoc.customermanager.CustomermanagerAppEvents;
import ch.hoc.customermanager.dto.AddressDTO;
import ch.hoc.customermanager.dto.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ch.hoc.customermanager.dto.AddressDTO.Fields.city;
import static ch.hoc.customermanager.dto.CustomerDTO.Fields.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIT {
    public static final String FIRST_NAME = "Thai Hoc";
    public static final String LAST_NAME = "Dang";
    public static final String EMAIL = "thaihoc.dang@hotmail.com";
    public static final String PHONE_NUMBER = "0799421526";
    public static final String NEW_PHONE_NUMBER = "0799999999";
    public static final String BIRTHDAY = "26/07/1993";
    public static final String STREET = "Abendstrasse 30";
    public static final int PLZ = 3018;
    public static final String CITY = "Bern";

    public static final String STREET2 = "Melchiorstrasse 13";
    public static final int PLZ2 = 3027;

    public static final String CUSTOMER_URL = "/customer";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomermanagerAppEvents customermanagerAppEvents;

    public static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCustomerAPI() throws Exception {
        getAllCustomersWithoutCustomers();
        getSaveCustomer(createCustomerDTO());
        Integer id = getIdFromCustomerList(getAllCustomers());
        updatedCustomer(getCustomer(id));
        getUpdatedCustomer(id);
        deleteCustomer(id);
        getAllCustomersWithoutCustomers();
    }

    private Integer getIdFromCustomerList(MvcResult mvcResult) throws Exception {
        String response = mvcResult.getResponse().getContentAsString();
        return JsonPath.parse(response).read("$[0]." + CustomerDTO.Fields.id);
    }

    private void updatedCustomer(MvcResult mvcResult) throws Exception {
        String response = mvcResult.getResponse().getContentAsString();
        CustomerDTO customerDTO = objectMapper.readValue(response, CustomerDTO.class);
        customerDTO.setPhoneNumber(NEW_PHONE_NUMBER);
        customerDTO.getAddresses().clear();
        getSaveCustomer(customerDTO);
    }

    private void getUpdatedCustomer(Integer id) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(CUSTOMER_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + CustomerDTO.Fields.id).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + phoneNumber).value(NEW_PHONE_NUMBER))
                .andExpect(MockMvcResultMatchers.jsonPath("$." + addresses + ".[*]").doesNotExist());
    }

    private void deleteCustomer(Integer id) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(CUSTOMER_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void getSaveCustomer(CustomerDTO customerDTO) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(CUSTOMER_URL)
                        .content(asJsonString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void getAllCustomersWithoutCustomers() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(CUSTOMER_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").doesNotExist());
    }

    private MvcResult getAllCustomers() throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .get(CUSTOMER_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]." + CustomerDTO.Fields.id).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]." + firstName).value(FIRST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]." + addresses).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]." + addresses + ".[*]." + AddressDTO.Fields.id).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].addresses.[0].city").value(CITY))
                .andReturn();
    }

    private MvcResult getCustomer(Integer id) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .get(CUSTOMER_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + CustomerDTO.Fields.id).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + firstName).value(FIRST_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$." + addresses).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + addresses + ".[*]." + AddressDTO.Fields.id).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$." + addresses + ".[0]." + city).value(CITY))
                .andReturn();
    }

    private AddressDTO createAddressDTO(String street, int plz) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(street);
        addressDTO.setPlz(plz);
        addressDTO.setCity(CITY);
        return addressDTO;
    }

    private CustomerDTO createCustomerDTO() throws ParseException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setEmail(EMAIL);
        customerDTO.setPhoneNumber(PHONE_NUMBER);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(BIRTHDAY);
        customerDTO.setBirthday(new Timestamp(date.getTime()));
        customerDTO.getAddresses().add(createAddressDTO(STREET, PLZ));
        customerDTO.getAddresses().add(createAddressDTO(STREET2, PLZ2));
        return customerDTO;
    }
}