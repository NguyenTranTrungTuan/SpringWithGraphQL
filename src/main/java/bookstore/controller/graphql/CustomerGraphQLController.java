package bookstore.controller.graphql;

import bookstore.dto.CustomerRequestDto;
import bookstore.entity.Customer;
import bookstore.service.CustomerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerGraphQLController {

    private final CustomerService customerService;

    public CustomerGraphQLController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @QueryMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @QueryMapping
    public Customer getCustomerById(@Argument Long id) {
        return customerService.getCustomerById(id);
    }

    @MutationMapping
    public Customer createCustomer(@Argument CustomerRequestDto input) {
        Customer customer = new Customer();
        customer.setFullName(input.getFullName());
        customer.setEmail(input.getEmail());
        customer.setPhone(input.getPhone());

        return customerService.createCustomer(customer);
    }

    @MutationMapping
    public Customer updateCustomer(@Argument Long id, @Argument CustomerRequestDto input) {
        Customer customerDetails = new Customer();
        customerDetails.setFullName(input.getFullName());
        customerDetails.setEmail(input.getEmail());
        customerDetails.setPhone(input.getPhone());

        return customerService.updateCustomer(id, customerDetails);
    }

    @MutationMapping
    public Boolean deleteCustomer(@Argument Long id) {
        customerService.deleteCustomer(id);
        return true;
    }
}