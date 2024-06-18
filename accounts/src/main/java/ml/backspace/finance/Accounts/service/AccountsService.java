package ml.backspace.finance.Accounts.service;

import ml.backspace.finance.Accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;

public interface AccountsService {
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccountDetails(String mobileNumber);

    boolean updateCustomer(CustomerDto customerDto);

    boolean deleteCustomer(String mobileNumber);
}
