package ml.backspace.finance.Accounts.mapper;

import ml.backspace.finance.Accounts.dto.CustomerDto;
import ml.backspace.finance.Accounts.entity.Customer;

public class CustomerMapper {
    public static void mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
